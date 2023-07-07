package com.eve.config;

import com.eve.dao.BizBuySellDao;
import com.eve.dao.database.SQLiteDB;
import com.eve.handlers.Executor;
import com.eve.handlers.biz_buy_sell.BizBuySellRunnable;
import com.eve.handlers.facebook_marketplace.FacebookMarketplaceCarRunnable;
import com.eve.notifier.IFTTTWebhookNotifier;
import com.eve.notifier.Notifier;

import java.util.concurrent.Executors;

public class SystemFactory {

    private static final int EXECUTOR_THREAD_POOL_SIZE = AppConfig.SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE;

    public static void initialize(String[] args) {
        initShutdownHook();
        JsonMapper.init();

        Notifier webhookNotifier =
                new IFTTTWebhookNotifier();

        SeleniumDriverFactory seleniumDriverFactory =
                new SeleniumDriverFactory();

        Executor executor = new Executor(
                Executors.newFixedThreadPool(EXECUTOR_THREAD_POOL_SIZE),
//                new FacebookMarketplaceCarRunnable(
//                        seleniumDriverFactory.newDriver(),
//                        webhookNotifier));
                new BizBuySellRunnable(seleniumDriverFactory.newDriver(),
                        new BizBuySellDao(new SQLiteDB(BizBuySellDao.TABLE_NAME)),
                        webhookNotifier));
        executor.start();

        LoggerFactory.getLogger(SystemFactory.class).info("Successfully initialized SystemFactory");
    }


    private static void initShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LoggerFactory.getLogger(SystemFactory.class).info("Shutting down ...");
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                LoggerFactory.getLogger(SystemFactory.class).error("Error caught while executing" +
                        "shutdown hook", e);
            }
        }));
    }
}
