package com.eve.handlers.facebook_marketplace;

import com.eve.config.AppConfig;
import com.eve.config.Logger;
import com.eve.util.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class FacebookMarketplaceCarRunnable implements Runnable {

    private final static Logger logger = Logger.getLogger(FacebookMarketplaceCarRunnable.class);

    public static final long RUN_EVERY_MS = AppConfig.FACEBOOK_MARKETPLACE_CAR_RUNNABLE_RUN_EVERY_MS;

    // Each item on facebook marketplace is displayed on the screen in a block.
    // These classes are the classes associated with each item block.
    private final static String MARKETPLACE_ITEM_ELEMENT_CLASS =
            "x9f619 x78zum5 x1r8uery xdt5ytf x1iyjqo2 xs83m0k x1e558r4 x150jy0e x1iorvi4 xjkvuk6 xnpuxes x291uyu x1uepa24";

    private final WebDriver chrome;
    private final CategoryHandler[] categoryHandler;

    public FacebookMarketplaceCarRunnable(WebDriver chrome, CategoryHandler... categoryHandler) {
        this.chrome = chrome;
        this.categoryHandler = categoryHandler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.log("Running FacebookMarketplaceCarRunnable");

                Wait.performActionThenWait(() -> {
                    Arrays.stream(categoryHandler).forEach(handler -> {
                        chrome.get(handler.getCategoryURL());
                        List<WebElement> latestItems = getLatestItems();
                        latestItems.forEach(handler::processItem);
                    });
                }, RUN_EVERY_MS);

                System.out.println("here 1");
            }
        } catch (Exception e) {
            logger.error("FacebookMarketplaceCarRunnable crashed ", e);
            throw e;
        } finally {
            chrome.quit();
        }
    }

    private List<WebElement> getLatestItems() {
        return chrome.findElements(By.cssSelector('.' + MARKETPLACE_ITEM_ELEMENT_CLASS.replace(' ', '.')));
    }
}
