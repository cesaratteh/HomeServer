package com.eve.handlers.biz_buy_sell;

import com.eve.config.LoggerFactory;
import com.eve.dao.BizBuySellDao;
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
        return dao.get(id).firstSeenSold() != null;
    }

    private boolean isSoldOnline(String id) {
        chrome.get(dao.get(id).url());
        List<WebElement> elements = chrome.findElements(By.tagName("h1"));

        return elements.stream().anyMatch(e -> e.getText().contains("The page you requested could not be found"));
    }

    private void markSold(String id) {
        BizBuySellDao.BizBuySellListing listing = dao.get(id);
        BizBuySellDao.BizBuySellListing updatedListing = new BizBuySellDao.BizBuySellListing(
                listing.id(),
                listing.title(),
                listing.financials(),
                listing.description(),
                listing.detailedInformation(),
                listing.broker(),
                listing.url(),
                listing.firstSeen(),
                listing.lastSeen(),
                new Date());

        dao.put(updatedListing);
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
                new Date(),
                listing.firstSeenSold());

        dao.put(updatedListing);
    }
}
