package com.roman.lv.mullvad;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.roman.lv.LVApplication;

public class ChromeDriverHelper {

    public static ChromeDriver build() {
        String driverPath = new File(LVApplication.class.getClassLoader().getResource("static/Linux/chromedriver").getFile()).getAbsolutePath();

        System.setProperty("webdriver.chrome.driver", "static/Linux/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-blink-features",
                "--disable-blink-features=AutomationControlled",
                "--no-sandbox",
                "--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36",
                "--window-size=1080,800",
                "--ignore-certificate-errors",
                "--test-type=webdriver");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("applicationCacheEnabled", false);
        options.merge(desiredCapabilities);

        ChromeDriver driver = new ChromeDriver(options);

        Map<String, Object> params = new HashMap<>();
        params.put("source", "Object.defineProperty(navigator, 'webdriver', { get: () => false })");
        params.put("source", "Object.defineProperty(navigator, 'languages', { get: () => ['en-GB', 'en', 'it'] })");
        params.put("source", "Object.defineProperty(navigator, 'plugins', { get: () => [0, 1, 2] })");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);
        return driver;
    }
}
