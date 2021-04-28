package com.roman.lv.mullvad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MullvadVPNService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MullvadVPNService.class);
    private static final List<String> BANNED_REGIONS = List.of("us", "ca", "au", "bg", "hu", "it", "pl", "rs");
    private static final int MAX_RETRIES = 10;

    private final List<String> proxies = new ArrayList<>();

    public MullvadVPNService() throws IOException {
        LOGGER.info("Initialise VPN connection");
        Runtime.getRuntime().exec("mullvad connect");
        updateAvailableProxies();
    }

    public void updateAvailableProxies() throws IOException {
        Pattern p = Pattern.compile("[a-z]{2}-[a-z]{3}-[0-9]{3}");
        Process process = Runtime.getRuntime().exec("mullvad relay list");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s;
        while ((s = reader.readLine()) != null) {
            Matcher matcher = p.matcher(s);
            if(matcher.find()) {
                String region = matcher.group().split("-")[0];
                String city = matcher.group().split("-")[1];
                if (!BANNED_REGIONS.contains(region)) {
                    proxies.add(region + " " + city + " " + matcher.group());
                }
            }
        }
        LOGGER.info("Proxy list loaded with {} servers", proxies.size());
    }

    public String switchProxy() throws IOException, InterruptedException {
        String proxy = proxies.get(new Random().nextInt(proxies.size()));
        Runtime.getRuntime().exec("mullvad relay set location " + proxy);
        Thread.sleep(1000L);
        int retries = 0;
        boolean connected = false;
        while (!connected && retries < MAX_RETRIES) {
            retries++;
            LOGGER.info("Connecting to {} - Attempt {}", proxy, retries);
            Process process = Runtime.getRuntime().exec("mullvad status");
            connected = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine().contains("Connected");
            Thread.sleep(1000L);
        }
        if (connected) {
            return proxy;
        } else {
            return null;
        }
    }
}
