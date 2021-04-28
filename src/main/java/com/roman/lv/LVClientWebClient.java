package com.roman.lv;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class LVClientWebClient {
    private static final Logger log = LoggerFactory.getLogger(LVClientWebClient.class);

    public boolean isWindowsOperatingSystem() {
        String os = System.getProperty("os.name");
        System.out.println("Using System Property: " + os);
            return os.contains("Windows");

    }

 /*
 Checks if the item is in stock
  */
    public String inStock(String url) {
        System.out.println("started checking " + url);
        String response ="Not Available";

                    ChromeOptions chromeOptions = new ChromeOptions();
                    //this does not work.
                    // chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
                    //disable chromecache
                   chromeOptions.setCapability("applicationCacheEnabled",false);

            if (isWindowsOperatingSystem()) {
                System.out.println("this is WIndows OS for sure");
               // System.setProperty("webdriver.gecko.driver", "target/classes/static/Windows/geckodriver.exe");
                System.setProperty("webdriver.chrome.driver", "target/classes/static/Windows/chromedriver.exe");
            }
            else
            {
                System.out.println("this is Linux OS for sure");
              System.setProperty("webdriver.chrome.driver", "classes/static/Linux/chromedriver");
               // System.setProperty("webdriver.gecko.driver", "classes/static/Linux/geckodriver");
            }
          chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
          WebDriver driver = new ChromeDriver(chromeOptions);
          //WebDriver driver = new FirefoxDriver();

        driver.manage().deleteAllCookies();
            try{
            System.out.println("URL" + url);

            driver.get(url);
            By by = new By.ByClassName("lv-stock-indicator");
            WebElement webElement = driver.findElement(by);
            response = webElement.getText();

            driver.quit();
        }
        catch(Exception e){
            e.printStackTrace();
            driver.quit();

        }
        return response;
    }
}
