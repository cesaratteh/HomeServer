package com.eve.config;

import com.eve.dao.BizBuySellDao;
import com.eve.dao.database.SQLiteDB;
import com.eve.handlers.Executor;
import com.eve.handlers.biz_buy_sell.BizBuySellRunnable;
import com.eve.notifier.IFTTTWebhookNotifier;
import com.eve.notifier.Notifier;

import java.util.concurrent.Executors;

public class SystemFactory {

    private static final int EXECUTOR_THREAD_POOL_SIZE = AppConfig.SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE;

    public static void initialize(String[] args) {
        try {
            initShutdownHook();
            PrometheusConfig.init();
            Docker.up();
            JsonMapper.init();

            Notifier webhookNotifier =
                    new IFTTTWebhookNotifier();
            SeleniumDriverFactory seleniumDriverFactory =
                    new SeleniumDriverFactory();

            BizBuySellDao bizBuySellDao =
                    new BizBuySellDao(new SQLiteDB(BizBuySellDao.TABLE_NAME));

            /**
             * Objects that are created inside the runnable factories
             * get re-created in case the runnable crashes.
             *
             * Objects that are created outside the factory, and
             * passed to it, are re-used when the thread crashes.
             */
            Executor executor = new Executor(
                    Executors.newFixedThreadPool(EXECUTOR_THREAD_POOL_SIZE),
                    () -> new BizBuySellRunnable(
                            seleniumDriverFactory.newDriver(),
                            bizBuySellDao,
                            webhookNotifier)
//                () ->  new FacebookMarketplaceCarRunnable(
//                            seleniumDriverFactory.newDriver(),
//                            webhookNotifier)
            );

            LoggerFactory.getLogger(SystemFactory.class)
                    .info("Successfully initialized SystemFactory, starting executor");
            executor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LoggerFactory.getLogger(SystemFactory.class).info("Shutting down ...");
                PrometheusConfig.shutdown();
                Docker.down();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                LoggerFactory.getLogger(SystemFactory.class).error("Error caught while executing" +
                        "shutdown hook", e);
            }
        }));
    }
}
