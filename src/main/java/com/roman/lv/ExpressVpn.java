package com.roman.lv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class ExpressVpn implements  VpnFactory{
    @Autowired
    ExpressVpnResourceLoader expressVpnResourceLoader;
    public  String randomStringFromSet() {
        Set<String> set = expressVpnResourceLoader.getItems();
        List<String> list = new ArrayList<>(set);

        int size = list.size();
        int randIdx = new Random().nextInt(size);

        String randomElem = list.get(randIdx);
        return randomElem;
    }
    @Override
    public void startVPN() {
     //becasue it is 4 characters identify the vpn
     String vpnServer =  randomStringFromSet().substring(0,4);
        try {
            boolean worked =false ;
            while (!worked) {
                System.out.println("inside vpn connection loop" + vpnServer);
                Process process  =  Runtime.getRuntime().exec("expressvpn connect " + vpnServer);

                //       Process process = Runtime.getRuntime().exec("expressvpn connect " + vpnServer );
                printResults(process);
             System.out.print("prewaiting");
             worked =    process.waitFor(10, TimeUnit.SECONDS);
             System.out.println("after waiting" + worked);
             //if failed will connect to usny
             vpnServer = "usny";

            }
        } catch (IOException | InterruptedException e) {
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
    @Override
    public void stopVPN() {
        try {
           boolean worked =false ;
           while (!worked) {
               Process process  =  Runtime.getRuntime().exec("expressvpn disconnect");
              // Process process = builder.start();
              worked = process.waitFor(10, TimeUnit.SECONDS);
               // Process process = Runtime.getRuntime().exec("expressvpn disconnect");
               printResults(process);
           }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
           }

}

