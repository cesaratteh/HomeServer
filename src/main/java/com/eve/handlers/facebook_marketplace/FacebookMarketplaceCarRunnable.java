package com.eve.handlers.facebook_marketplace;

import com.eve.config.Logger;
import com.eve.notifier.Notifier;
import com.eve.util.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookMarketplaceCarRunnable implements Runnable {

    private final static Logger logger = Logger.getLogger(FacebookMarketplaceCarRunnable.class);

    private final static String MARKETPLACE_QUERY_URL =
            "https://www.facebook.com/marketplace/sanfrancisco/vehicles?sortBy=creation_time_descend";

    // Each item on facebook marketplace is displayed on the screen in a block.
    // These classes are the classes associated with each item block.
    private final static String MARKETPLACE_ITEM_ELEMENT_CLASS =
            "x9f619 x78zum5 x1r8uery xdt5ytf x1iyjqo2 xs83m0k x1e558r4 x150jy0e x1iorvi4 xjkvuk6 xnpuxes x291uyu x1uepa24";

    // Regex used to extract marketplace item url.
    // Example - https://www.facebook.com/marketplace/item/582385194079531
    private final static String MARKETPLACE_ITEM_URL_REGEX =
            "https://www\\.facebook\\.com/marketplace/item/[0-9]+";

    private final WebDriver chrome;
    private final Notifier notifier;

    public FacebookMarketplaceCarRunnable(WebDriver chrome, Notifier notifier) {
        this.chrome = chrome;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        logger.log("Running FacebookMarketplaceCarRunnable");
        chrome.get(MARKETPLACE_QUERY_URL);

        List<Car> latest10Cars = getLatest10Cars();
        logger.log("Found the following cars: " + latest10Cars);

        chrome.quit();
    }

    private List<Car> getLatest10Cars() {
        List<Car> results = new ArrayList<>();

        List<WebElement> elements = chrome.findElements(By.cssSelector('.' + MARKETPLACE_ITEM_ELEMENT_CLASS.replace(' ', '.')));

        for (int i = 0; i < 12; i++) {
            results.add(new Car(elements.get(i)));
        }

        return results;
    }

    private class Car {
        String name;
        int price;
        String location;
        Optional<String> miles;
        String url;

        public Car(WebElement carElement) {
            String text = carElement.getText();

            String[] lines = text.split("\n");
            String priceLine = lines[0];
            String carLine = lines[1];
            String locationLine = lines[2];
            Optional<String> milesLine = lines.length > 3 ? Optional.of(lines[3]) : Optional.empty();

            this.name = carLine;
            this.price = PriceUtil.toInt(priceLine);
            this.location = locationLine;
            this.miles = milesLine;

            String url = carElement.findElement(By.tagName("a")).getAttribute("href");
            Matcher matcher = Pattern.compile(MARKETPLACE_ITEM_URL_REGEX).matcher(url);
            this.url = matcher.find() ? matcher.group() : null;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", miles='" + miles + '\'' +
                    ", location='" + location + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
