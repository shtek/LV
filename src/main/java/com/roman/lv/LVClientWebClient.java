package com.roman.lv;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        String response ="Not Available";

        //            ChromeOptions chromeOptions = new ChromeOptions();
            if (isWindowsOperatingSystem()) {
                System.out.println("this is WIndows OS for sure");

                System.setProperty("webdriver.gecko.driver","browserDrivers/geckodriver.exe"); // Setting system properties of FirefoxDriver
               // System.setProperty("webdriver.chrome.driver", "browserDrivers/chromedriver.exe");
            }
            else
            {
                System.out.println("this is Linux OS for sure");
                //System.setProperty("webdriver.chrome.driver", "browserDrivers/chromedriver");
                System.setProperty("webdriver.gecko.driver","browserDrivers/geckodriver");

            }
            //Proxy proxy = new org.openqa.selenium.Proxy();
            //proxy.setSslProxy("shtek:YvJ9b@uTV9AZ@Xs@us-wa.proxymesh.com:31280");
            // proxy.setHttpProxy("shtek:YvJ9b@uTV9AZ@Xs@us-wa.proxymesh.com:31280");
            // chromeOptions.setCapability("proxy", proxy);
         //   WebDriver driver = new ChromeDriver(chromeOptions);
            WebDriver driver = new FirefoxDriver();
            try{
            System.out.println("URL" + url);
            driver.get(url);
            By by = new By.ByClassName("lv-stock-indicator");
            WebElement webElement = driver.findElement(by);
            response = webElement.getText();
          //  driver.close();
            driver.quit();
        }
        catch(Exception e){
            e.printStackTrace();
            driver.quit();

        }
        return response;
    }
}
