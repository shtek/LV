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
        String [] urlAndemail = urlwithemail.split("DIVIDER");
        String url = urlAndemail[0];
        System.out.println("url " + url);
        String emailAddress = urlAndemail[1];
        System.out.println(emailAddress + "email");

        return new ArrayList<>();
    }
}
