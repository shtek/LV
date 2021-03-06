package com.roman.lv.mullvad;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.roman.lv.EmailService;
import com.roman.lv.LoadResourceConfig;
import com.roman.lv.RomanStringUtils;

@Component
@ConditionalOnProperty(value = "vpn.mullvad")
public class AvailabilityCheckScheduler {

    private final static Logger LOGGER = LoggerFactory.getLogger(AvailabilityCheckScheduler.class);

    private final LoadResourceConfig loadResourceConfig;
    private final ExecutorService executorService;
    private final EmailService emailService;
    private final MullvadVPNService mullvadVPNService;

    public AvailabilityCheckScheduler(
            LoadResourceConfig loadResourceConfig,
            EmailService emailService,
            MullvadVPNService mullvadVPNService) throws IOException {
        this.loadResourceConfig = loadResourceConfig;
        //this.executorService = Executors.newFixedThreadPool(loadResourceConfig.getItems().size());
        this.executorService = Executors.newFixedThreadPool(1);
        this.emailService = emailService;
        this.mullvadVPNService = mullvadVPNService;
        ChromeDriverHelper.prepareDriver();
    }

    @Scheduled(fixedRateString = "${scheduler.interval:60000}", initialDelay = 5_000L)
    private void runTask() throws InterruptedException, IOException {
        String proxy = mullvadVPNService.switchProxy();
        if (proxy == null) {
            mullvadVPNService.updateAvailableProxies();
            return;
        }
        List<CompletableFuture<SimpleEntry<String, AvailabilityStatus>>> completableFutures = loadResourceConfig.getItems().stream()
                .parallel()
                .map(item -> supplyAsync(() -> {
                    ChromeDriver driver = ChromeDriverHelper.build();
                    try {
                        String url = RomanStringUtils.getURL(item);
                        driver.get(url);
                        AvailabilityStatus availabilityStatus = checkAvailability(driver);
                        switch (availabilityStatus) {
                            case ACCESS_DENIED: new SimpleEntry<>(item, AvailabilityStatus.ACCESS_DENIED);
                            case INFORMATION_NOT_AVAILABLE:
                                driver.navigate().refresh();
                                return new SimpleEntry<>(item, checkAvailability(driver));
                            default: return new SimpleEntry<>(item, availabilityStatus);
                        }
                    } catch (Throwable throwable) {
                        LOGGER.error(throwable.getMessage());
                    } finally {
                        driver.quit();
                    }
                    return new SimpleEntry<>(item, AvailabilityStatus.UNKNOWN);
                }, executorService))
                .collect(Collectors.toList());

        Map<String, AvailabilityStatus> urlToAvailability = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));

        urlToAvailability.entrySet().stream()
                .filter(status -> status.getValue().equals(AvailabilityStatus.UNKNOWN)
                        || status.getValue().equals(AvailabilityStatus.INFORMATION_NOT_AVAILABLE)
                        || status.getValue().equals(AvailabilityStatus.ACCESS_DENIED))
                .forEach(entry -> LOGGER.warn("{} Failed for {} with reason: {}", proxy, RomanStringUtils.getURL(entry.getKey()), entry.getValue().name()));

        urlToAvailability.entrySet().stream()
                .filter(status -> (status.getValue().equals(AvailabilityStatus.IN_STOCK) || status.getValue().equals(AvailabilityStatus.AVAILABLE)))
                .forEach(entry -> {
                            emailService.sendSimpleMessage(
                                    RomanStringUtils.getURL(entry.getKey()),
                                    RomanStringUtils.getEmails(entry.getKey()));
                            loadResourceConfig.removeItem(entry.getKey());
                        }
                );
    }

    private AvailabilityStatus checkAvailability(ChromeDriver driver) {
        if (AvailabilityStatus.ACCESS_DENIED.getWebCode().equalsIgnoreCase(driver.getTitle())) {
            return AvailabilityStatus.ACCESS_DENIED;
        }
        WebDriverWait wait = new WebDriverWait(driver, 20L);
        wait.until(webDriver -> webDriver.findElement(By.className("lv-stock-indicator")));
        List<WebElement> maybeAvailable = driver.findElements(new By.ByClassName("lv-stock-indicator"));

        return maybeAvailable.stream()
                .map(webElement -> AvailabilityStatus.fromWebCode(webElement.getText().toLowerCase()))
                .filter(availability -> availability.equals(AvailabilityStatus.IN_STOCK)
                        || availability.equals(AvailabilityStatus.AVAILABLE)
                        || availability.equals(AvailabilityStatus.OUT_OF_STOCK))
                .findFirst()
                .orElse(AvailabilityStatus.INFORMATION_NOT_AVAILABLE);
    }
}
