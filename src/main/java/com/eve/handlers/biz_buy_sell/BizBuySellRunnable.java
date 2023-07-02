package com.eve.handlers.biz_buy_sell;

import com.eve.config.AppConfig;
import com.eve.config.Logger;
import com.eve.dao.BizBuySellDao;
import com.eve.notifier.Notifier;
import com.eve.util.Wait;
import org.openqa.selenium.WebDriver;

public class BizBuySellRunnable implements Runnable {

    public static final long FETCH_LISTINGS_EVERY_MS = AppConfig.BIZ_BUY_SELL_FETCH_NEW_LISTINGS_EVERY_X_MS;
    public static final long CHECK_LISTINGS_STILL_UP_EVERY_X_MS = AppConfig.BIZ_BUY_SELL_CHECK_LISTINGS_STILL_UP_EVERY_X_MS;

    private static long lastUpdateTime = System.currentTimeMillis();

    private final NewListingsPuller newListingsPuller;
    private final SoldListingsSweeper soldListingsSweeper;

    public BizBuySellRunnable(WebDriver chrome, BizBuySellDao dao, Notifier notifier) {
        this.newListingsPuller = new NewListingsPuller(chrome, dao);
        this.soldListingsSweeper = new SoldListingsSweeper(chrome, dao);
    }

    private static boolean shouldRunSoldListingSweeper() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastUpdateTime >= CHECK_LISTINGS_STILL_UP_EVERY_X_MS;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Wait.waitThenPerformAction(newListingsPuller::pullLatestListings, FETCH_LISTINGS_EVERY_MS);

                if (shouldRunSoldListingSweeper()) {
                    soldListingsSweeper.sweepAllListingsAndMarkSoldOnes();
                    lastUpdateTime = System.currentTimeMillis();
                }
            } catch (Exception e) {
                Logger.error("BizBuySell handler crashed ", e);
            }
        }
    }
}
