package com.eve.handlers.biz_buy_sell;

import com.eve.dao.BizBuySellDao;
import com.eve.dao.BizBuySellListing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewListingsPuller {

    private static final Pattern BIZ_LISTING_URL_REGEX =
            Pattern.compile("https://www\\.bizbuysell\\.com/Business-Opportunity/([A-Za-z0-9]+(-[A-Za-z0-9]+)+)/[0-9]+/", Pattern.CASE_INSENSITIVE);

    private final WebDriver chrome;
    private final BizBuySellDao dao;
    private final String listingsPageUrl;

    public NewListingsPuller(WebDriver chrome, BizBuySellDao dao, String listingsPageUrl) {
        this.chrome = chrome;
        this.dao = dao;
        this.listingsPageUrl = listingsPageUrl;
    }

    public void pullLatestListings() {
        List<String> listingsURLs = getListingsURLs(listingsPageUrl);

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
        BizBuySellListing listing = dao.get(listingId);
        BizBuySellListing updatedListing = new BizBuySellListing(
                listing.getId(),
                listing.getTitle(),
                listing.getFinancials(),
                listing.getDescription(),
                listing.getDetailedInformation(),
                listing.getBroker(),
                listing.getUrl(),
                listing.getFirstSeen(),
                new Date(),
                listing.getFirstSeenSold());

        dao.put(updatedListing);
    }

    private void storeListing(String listingId, String listingUrl) {
        chrome.get(listingUrl);

        String title = chrome.findElement(By.className("bfsTitle")).getText();
        String financials = chrome.findElement(By.className("financials")).getText();
        String description = chrome.findElement(By.className("businessDescription")).getText();
        String detailedInformation = chrome.findElement(By.className("listingProfile_details")).getText();
        String broker = chrome.findElement(By.className("broker")).getText();

        BizBuySellListing listing = new BizBuySellListing(
                listingId,
                title,
                financials,
                description,
                detailedInformation,
                broker,
                listingUrl,
                new Date(),
                new Date(),
                null
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

        chrome.get(pageURL);
        List<WebElement> anchorTags = chrome.findElements(By.tagName("a"));
        for (WebElement anchorTag : anchorTags) {
            String href = anchorTag.getAttribute("href");
            if (href == null) continue;

            Matcher matcher = BIZ_LISTING_URL_REGEX.matcher(href);
            if (matcher.find()) {
                String matchedURL = matcher.group(0);
                urls.add(matchedURL);
            }
        }

        return urls;
    }
}
