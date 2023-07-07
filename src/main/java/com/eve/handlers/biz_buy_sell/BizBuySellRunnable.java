package com.eve.handlers.biz_buy_sell;

import com.eve.config.AppConfig;
import com.eve.config.LoggerFactory;
import com.eve.dao.BizBuySellDao;
import com.eve.notifier.Notifier;
import com.eve.util.Wait;
import org.openqa.selenium.WebDriver;

public class BizBuySellRunnable implements Runnable {

    public static final String BIZ_BUY_SELL_NATIONWIDE_3D_QUERY_URL = AppConfig.BIZ_BUY_SELL_RUNNABLE_NATIONWIDE_3DAYS_QUERY_URL;
    public static final long FETCH_LISTINGS_EVERY_MS = AppConfig.BIZ_BUY_SELL_FETCH_NEW_LISTINGS_EVERY_X_MS;
    public static final long CHECK_LISTINGS_STILL_UP_EVERY_X_MS = AppConfig.BIZ_BUY_SELL_CHECK_LISTINGS_STILL_UP_EVERY_X_MS;

    private static long lastUpdateTime = System.currentTimeMillis();

    private static boolean isFirstRun = true;
    private final NewListingsPuller newListingsPuller;
    private final SoldListingsSweeper soldListingsSweeper;

    public BizBuySellRunnable(WebDriver chrome, BizBuySellDao dao, Notifier notifier) {
        this.newListingsPuller = new NewListingsPuller(chrome, dao, BIZ_BUY_SELL_NATIONWIDE_3D_QUERY_URL);
        this.soldListingsSweeper = new SoldListingsSweeper(chrome, dao);
    }

    private static boolean shouldRunSoldListingSweeper() {
        if (isFirstRun) {
            isFirstRun = false;
            return true;
        }

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
                LoggerFactory.getLogger(this.getClass()).error("BizBuySell handler crashed ", e);
            }
        }
    }
}
