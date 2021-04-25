package com.roman.lv.mullvad;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.roman.lv.LVApplication;

public class ChromeDriverHelper {

    public static ChromeDriver build() {
        String driverPath = new File(LVApplication.class.getClassLoader().getResource("static/Linux/chromedriver").getFile()).getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-blink-features",
                "--disable-blink-features=AutomationControlled",
                "--no-sandbox");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("applicationCacheEnabled", false);
        options.merge(desiredCapabilities);

        ChromeDriver driver = new ChromeDriver(options);

        Map<String, Object> params = new HashMap<>();
        params.put("source", "Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);
        driver.manage().deleteAllCookies();
        return driver;
    }
}
