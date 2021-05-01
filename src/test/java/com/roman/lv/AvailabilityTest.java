package com.roman.lv;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.roman.lv.mullvad.ChromeDriverHelper;

public class AvailabilityTest {

    private ChromeDriver driver;

    @BeforeEach
    public void setup() {
        driver = ChromeDriverHelper.build();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSeleniumOutOfStock() {
        URI uriOOS = URI.create("https://us.louisvuitton.com/eng-us/products/pochette-accessoires-damier-azur-canvas-005868");

        driver.get(uriOOS.toString());
        WebDriverWait wait = new WebDriverWait(driver, 5L);
        wait.until(d -> d.findElement(By.className("lv-stock-indicator")));
        driver.navigate().refresh();
        wait.until(d -> d.findElement(By.className("lv-stock-indicator")));
        List<WebElement> maybeAvailable = driver.findElements(new By.ByClassName("lv-stock-indicator"));

        assertThat(maybeAvailable).anyMatch(webElement -> webElement.getText().equals("Out of stock"));
    }

    @Test
    public void testSeleniumInStock() {
        URI uriINS = URI.create("https://us.louisvuitton.com/eng-us/products/reversible-leather-technical-jacket-nvprod1780327v#1A7XON");

        driver.get(uriINS.toString());
        WebDriverWait wait = new WebDriverWait(driver, 3_000L);
        wait.until(d -> d.findElement(By.className("lv-stock-indicator")));
        driver.navigate().refresh();
        wait.until(d -> d.findElement(By.className("lv-stock-indicator")));
        List<WebElement> maybeAvailable = driver.findElements(new By.ByClassName("lv-stock-indicator"));

        assertThat(maybeAvailable).anyMatch(webElement -> webElement.getText().equals("In stock"));
    }
}
