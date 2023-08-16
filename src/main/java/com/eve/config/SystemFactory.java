package com.eve.config;

import com.eve.dao.BizBuySellDao;
import com.eve.dao.database.SQLiteDB;
import com.eve.handlers.Executor;
import com.eve.handlers.biz_buy_sell.BizBuySellRunnable;
import com.eve.handlers.facebook_marketplace.FacebookMarketplaceCarRunnable;
import com.eve.handlers.facebook_marketplace.cars.VehicleCategoryHandler;
import com.eve.notifier.IFTTTWebhookNotifier;
import com.eve.notifier.Notifier;

import java.util.concurrent.Executors;

public class SystemFactory {

    private final static Logger logger = Logger.getLogger(SystemFactory.class);

    private static final int EXECUTOR_THREAD_POOL_SIZE = AppConfig.SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE;

    public static void initialize(String[] args) {
        try {
            initShutdownHook();
            Prometheus.init();
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
                            webhookNotifier),
                    () ->  new FacebookMarketplaceCarRunnable(
                            seleniumDriverFactory.newDriver(),
                            new VehicleCategoryHandler(webhookNotifier)
                    )
            );

            logger.log("Successfully initialized SystemFactory, starting executor");
            executor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.log("Shutting down ...");
                Prometheus.shutdown();
                Docker.down();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                logger.error("Error caught while executing shutdown hook", e);
            }
        }));
    }
}
