package com.eve.handlers.biz_buy_sell;

import com.eve.config.LoggerFactory;
import com.eve.dao.BizBuySellDao;
import com.eve.dao.BizBuySellListing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;

public class SoldListingsSweeper {

    private final WebDriver chrome;
    private final BizBuySellDao dao;

    public SoldListingsSweeper(WebDriver chrome, BizBuySellDao dao) {
        this.chrome = chrome;
        this.dao = dao;
    }

    public void sweepAllListingsAndMarkSoldOnes() {
        List<String> allIds = dao.getAllIds();
        int markedSold = 0;
        int alreadySold = 0;

        for (String id : allIds) {
            if (isSoldInDao(id)) {
                alreadySold++;
            } else {
                if (isSoldOnline(id)) {
                    markSold(id);
                    markedSold++;
                } else {
                    updateLastSeenDate(id);
                }
            }
        }

        LoggerFactory.getLogger(this.getClass()).info("Sweeper results: TotalItems/WasAlreadySold/JustMarkedAsSold " +
                allIds.size() + "/" + alreadySold + "/" + markedSold);
    }

    private boolean isSoldInDao(String id) {
        return dao.get(id).getFirstSeenSold() != null;
    }

    private boolean isSoldOnline(String id) {
        chrome.get(dao.get(id).getUrl());
        List<WebElement> elements = chrome.findElements(By.tagName("h1"));

        return elements.stream().anyMatch(e -> e.getText().contains("The page you requested could not be found"));
    }

    private void markSold(String id) {
        BizBuySellListing listing = dao.get(id);
        BizBuySellListing updatedListing = new BizBuySellListing(
                listing.getId(),
                listing.getTitle(),
                listing.getFinancials(),
                listing.getDescription(),
                listing.getDetailedInformation(),
                listing.getBroker(),
                listing.getUrl(),
                listing.getFirstSeen(),
                listing.getLastSeen(),
                new Date());

        dao.put(updatedListing);
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
}
