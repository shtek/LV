package com.roman.lv;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LVClientWebClientTest {
   @Autowired
    ProxyPopulator proxyPopulator;
  @Autowired
    FreeProxySelector  proxySelector ;
       @Test
        public void getElements() throws Exception {
      //      proxyPopulator.populateProxies();
      ChromeOptions chromeOptions = new ChromeOptions();
     //      System.setProperty("http.proxyHost", "178.20.137.178");
     //      System.setProperty("http.proxyPort", "43980");
      System.setProperty("webdriver.chrome.driver","target/classes/static/Windows/chromedriver.exe"); // Setting system properties of FirefoxDriver

       //     org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
          //  proxy.setHttpProxy(proxySelector.getProxy().address().toString().replace("/",""));


   // chromeOptions.setCapability("proxy", proxy);

   // chromeOptions.addArguments("user-agent=Mozilla/5.0 (Linux; Android 10; SM-A102U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Mobile Safari/537.36");

        //chromeOptions.addArguments("--headless", "--window-size=1920,1200");



    for (int i = 0; i < 1; i++) {
        System.out.println(i);
        Thread.sleep(5000);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("chrome://settings/clearBrowserData");
        Thread.sleep(10000);
        driver.findElement(By.xpath("//*[@id='clearBrowsingDataConfirm']")).click();
        driver.get("http://uk.louisvuitton.com/eng-gb/products/outdoor-slingbag-k45-nvprod2780009v");
        // driver.get("http://agent1973.herokuapp.com/ip");
        //driver.get("http://www.bbc.com");
//      String  source =driver.getPageSource();
  //    System.out.print(source);
     By by = new By.ByClassName("lv-stock-indicator");

        WebElement webElement = driver.findElement(by);
        System.out.println(webElement.getText());
       driver.quit();

    }

}


    public void testHermes(){
        URL url = null;
        String response = null;
     HttpURLConnection con = null;
        try {
            url = new URL("hermes url");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            response =  content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        }

      //  @Test
    public void toestAddrr() throws IOException {
            java.net.Proxy webProxy
                       = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("103.227.254.118", 2021));
            System.out.println(webProxy.address());
            String host = webProxy.address().toString().replace("/","").split(":")[0];
            System.out.print("---££"+ host);
           InetAddress addr=InetAddress.getByName(host);//here type proxy server ip
           System.out.println("----->" + addr.getHostName());

          System.out.println(addr.isReachable(1000)); // 1 second time for response

        }

}
