package com.roman.lv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class StockChecker implements Callable<List<String>> {
    LVClientWebClient webClient;
    String urlwithemail;
    public  StockChecker(LVClientWebClient client, String urlwithemail){
        webClient = client;
        this.urlwithemail=urlwithemail;
    }
    public List<String>  call() {
        String url = RomanStringUtils.getURL(urlwithemail);
        System.out.println("url " + url);
        String[] emailAddresses = RomanStringUtils.getEmails(urlwithemail);
        String xml = webClient.inStock(url);
        List<String> res = new ArrayList<>();

        res.add(xml);
        res.add(urlwithemail);
        return  res;
    }
}
