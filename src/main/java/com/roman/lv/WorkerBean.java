package com.roman.lv;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;

@Service
public class WorkerBean {

    private static final Logger log = LoggerFactory.getLogger(WorkerBean.class);
    @Autowired
    LVClientWebClient webClient;

    @Autowired
    Counter counter;


    @Autowired
    LoadResourceConfig loadResourceConfig;
    @Autowired
    EmailService emailService;




    /*
    1. received new items
    2. filter out only branded items
    3. Add items to storage
    4. if any new items were added to storage then alert
   */
    public synchronized String  checkForNewArrivals() throws InterruptedException {

        System.out.println("isnide check for new arraivals");
        /*
        items are of the structure:
        https://uk.louisvuitton.com/eng-gb/products/loya-sunglasses-nvprod2810055v#Z1457W:shtek@yahoo.com
         */
        Set<String> items = loadResourceConfig.getItems();
        //make it concurrent

        Set<String> remove =  ConcurrentHashMap.newKeySet();
        //start vpn before all scans
        System.out.println("starting vpn");
        startVPN();
        System.out.println("just started vpn");
        //fir each item in the items do
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        List<StockChecker> checkers = new ArrayList<>();

        items.stream().forEach(s->{
            checkers.add(new StockChecker(webClient,s));
        });
        Collection<Future<List<String>>> futures = executorService.invokeAll(checkers);
        List<List <String>>  list = new ArrayList<>();
        futures.stream().forEach(f->{

            try {
                List<String> l =   f.get();
                String lineforRemovalURLandEmails = l.get(1);
                String xml = l.get(0);
                if (inStock(xml))
                {
                    //not using counter but remove the item from scanning
                    //  if(counter.getCounter()==false)
                    remove.add(lineforRemovalURLandEmails);
                    emailService.sendSimpleMessage(RomanStringUtils.getURL(lineforRemovalURLandEmails), RomanStringUtils.getEmails(lineforRemovalURLandEmails));

                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });
        //stop vpn after one pass
        System.out.println("stopping vpn");
        stopVPN();
        System.out.println("just stopped VPN");
        //after one pass , remove the items from scan list,
        //so i dont send the same email over and over

        //come up with better name for the class
        if( remove != null)
            loadResourceConfig.removeItems(remove);


        return   "checked for items";
    }
    /*
    Available - in UK
    In stock -  in USA
     */
    private boolean inStock(String xml){
        System.out.println("-->" + xml + "---");
        return  (xml.equals("Available") || (xml.equals("In stock"))) ;

    }
/*
perhaps I can have a wait of some kind, and if I am not able to connect after few seconds , then reattempt again
i think sometimes connection is not good
 */
    private void startVPN(){
        try {
            Process process = Runtime.getRuntime().exec("protonvpn-cli c -r");
            printResults(process);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void stopVPN(){
        try {
            Process process = Runtime.getRuntime().exec("protonvpn-cli disconnect");
            printResults(process);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }



}