package com.roman.lv.mullvad;

import static org.apache.commons.io.FileUtils.copyURLToFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChromeDriverHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChromeDriverHelper.class);

    public static ChromeDriver build() {
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

        return new ChromeDriver(options);
    }

    public static void prepareDriver() throws IOException {
        LOGGER.info("Preparing Chrome driver");
        ClassLoader classLoader = ChromeDriverHelper.class.getClassLoader();
        URL resource = classLoader.getResource("static/Linux/chromedriver");
        File f = new File("driver");
        if (!f.exists()) {
            f.mkdirs();
        }
        File chromeDriver = new File("driver" + File.separator + "chromedriver");
        if (!chromeDriver.exists()) {
            chromeDriver.createNewFile();
            copyURLToFile(resource, chromeDriver);
        }
        Runtime.getRuntime().exec("chmod u+x "+ chromeDriver.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
    }
}
