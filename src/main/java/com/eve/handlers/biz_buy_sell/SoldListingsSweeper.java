package com.eve.handlers.biz_buy_sell;

import com.eve.config.Logger;
import com.eve.config.Prometheus;
import com.eve.dao.BizBuySellDao;
import com.eve.dao.BizBuySellListing;
import io.prometheus.client.Counter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;

public class SoldListingsSweeper {

    private final static Logger LOGGER = Logger.getLogger(SoldListingsSweeper.class);

    private final static Counter UPDATED_LAST_SEEN = Prometheus.counter(SoldListingsSweeper.class, "UpdatedLastSeen");
    private final static Counter MARKED_SOLD = Prometheus.counter(SoldListingsSweeper.class, "MarkedSold");

    private final WebDriver chrome;
    private final BizBuySellDao dao;

    public SoldListingsSweeper(WebDriver chrome, BizBuySellDao dao) {
        this.chrome = chrome;
        this.dao = dao;
    }

    public void sweepAllListingsAndMarkSoldOnes() {
        List<String> allIds = dao.getAllIds();

        for (String id : allIds) {
            if (isSoldInDao(id)) {
            } else {
                if (isSoldOnline(id)) {
                    markSold(id);
                    MARKED_SOLD.inc();
                } else {
                    updateLastSeenDate(id);
                    UPDATED_LAST_SEEN.inc();
                }
            }
        }

        LOGGER.log("Sweeper results: TotalItems/UpdatedLastSeen/MarkedSold " +
                allIds.size() + "/" + UPDATED_LAST_SEEN.get() + "/" + MARKED_SOLD.get());
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
