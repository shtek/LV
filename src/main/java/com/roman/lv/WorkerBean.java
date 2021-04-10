package com.roman.lv;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    public synchronized String  checkForNewArrivals() {

        System.out.println("isnide check");
        Set<String> items = loadResourceConfig.getItems();

        Set<String> remove = new HashSet<>();
        //fir each item in the items do
        items.stream().forEach(s->{

            String xml = webClient.inStock(s);

            if (inStock(xml))
            {
              //not using counter but remove the item from scanning
              //  if(counter.getCounter()==false)
                    remove.add(s);
                    emailService.sendSimpleMessage(s);
              //not using counter but remove item from scanning
              //  counter.setCounter(true);
            }
        });
        //after one pass , remove the items from scan list,
        //so i dont send the same email over and over

        //come up with better name for the class
        if( remove != null)
          loadResourceConfig.removeItems(remove);


        return   "checked for items";
        }
     private boolean inStock(String xml){
        System.out.println("-->" + xml + "---");
        return  xml.equals("Available");

     }




}
