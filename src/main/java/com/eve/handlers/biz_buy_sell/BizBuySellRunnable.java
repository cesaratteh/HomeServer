package com.eve.handlers.biz_buy_sell;

import com.eve.config.AppConfig;
import com.eve.dao.BizBuySellDao;
import com.eve.notifier.Notifier;
import com.eve.util.Logger;
import com.eve.util.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BizBuySellRunnable implements Runnable {

    private static final String BIZ_BUY_SELL_WASHINGTON_QUERY_URL =
            "https://www.bizbuysell.com/washington-established-businesses-for-sale/?q=ZGxhPTM%3D";

    private static final Pattern BIZ_LISTING_URL_REGEX =
            Pattern.compile("https://www\\.bizbuysell\\.com/Business-Opportunity/([A-Za-z0-9]+(-[A-Za-z0-9]+)+)/[0-9]+/", Pattern.CASE_INSENSITIVE);

    public static final long DRIVER_DELAY = AppConfig.BIZ_BUY_SELL_DRIVER_DELAY_IN_SECONDS;


    private final WebDriver chrome;
    private final BizBuySellDao dao;
    private final Notifier notifier;

    public BizBuySellRunnable(WebDriver chrome, BizBuySellDao dao, Notifier notifier) {
        this.chrome = chrome;
        this.dao = dao;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Wait.waitThenPerformAction(this::doRun, DRIVER_DELAY);
                break;
            } catch (Exception e) {
                Logger.error("BizBuySell handler crashed", e);
            }
        }
    }

    private void doRun() {
        List<String> listingsURLs = getListingsURLs(BIZ_BUY_SELL_WASHINGTON_QUERY_URL);

        for (String listingUrl : listingsURLs) {
            String listingId = extractListingIdFromURL(listingUrl);

            if (dao.isPresent(listingId)) {
                updateLastSeenDate(listingId);
            } else {
                storeListing(listingId, listingUrl);
            }
        }
    }

    private void updateLastSeenDate(String listingId) {
        BizBuySellDao.BizBuySellListing listing = dao.get(listingId);
        BizBuySellDao.BizBuySellListing updatedListing = new BizBuySellDao.BizBuySellListing(
                listing.id(),
                listing.title(),
                listing.financials(),
                listing.description(),
                listing.detailedInformation(),
                listing.broker(),
                listing.url(),
                listing.firstSeen(),
                new Date());

        dao.put(updatedListing);
    }

    private void storeListing(String listingId, String listingUrl) {
        Wait.performActionThenWait(() -> chrome.get(listingUrl), DRIVER_DELAY);

        String title = chrome.findElement(By.className("bfsTitle")).getText();
        String financials = chrome.findElement(By.className("financials")).getText();
        String description = chrome.findElement(By.className("businessDescription")).getText();
        String detailedInformation = chrome.findElement(By.className("listingProfile_details")).getText();
        String broker = chrome.findElement(By.className("broker")).getText();

        BizBuySellDao.BizBuySellListing listing = new BizBuySellDao.BizBuySellListing(
                listingId,
                title,
                financials,
                description,
                detailedInformation,
                broker,
                listingUrl,
                new Date(),
                new Date()
        );

        dao.put(listing);
    }

    private String extractListingIdFromURL(String URL) {
        int lastIndex = URL.lastIndexOf('/');
        int secondLastIndex = URL.lastIndexOf('/', lastIndex - 1);

        return URL.substring(secondLastIndex + 1, lastIndex);
    }

    private List<String> getListingsURLs(String pageURL) {
        List<String> urls = new ArrayList<>();

        Wait.performActionThenWait(() -> chrome.get(pageURL), DRIVER_DELAY);
        List<WebElement> anchorTags = chrome.findElements(By.tagName("a"));
        for (WebElement anchorTag : anchorTags) {
            String href = anchorTag.getAttribute("href");
            if(href == null) continue;

            Matcher matcher = BIZ_LISTING_URL_REGEX.matcher(href);
            if (matcher.find()) {
                String matchedURL = matcher.group(0);
                urls.add(matchedURL);
            }
        }

        return urls;
    }
}
