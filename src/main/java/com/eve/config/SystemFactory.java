package com.eve.config;

import com.eve.handlers.Executor;
import com.eve.handlers.facebook_marketplace.FacebookMarketplaceCarRunnable;
import com.eve.notifier.IFTTTWebhookNotifier;
import com.eve.notifier.Notifier;
import com.eve.util.Logger;

import java.util.concurrent.Executors;

public class SystemFactory {

    private static final int EXECUTOR_THREAD_POOL_SIZE = AppConfig.SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE;

    public static void initialize(String[] args) {
        Notifier webhookNotifier =
                new IFTTTWebhookNotifier();

        SeleniumDriverFactory seleniumDriverFactory =
                new SeleniumDriverFactory();

        Executor executor = new Executor(
                Executors.newFixedThreadPool(EXECUTOR_THREAD_POOL_SIZE),
                new FacebookMarketplaceCarRunnable(
                        seleniumDriverFactory.newDriver(),
                        webhookNotifier));
        executor.start();

        Logger.log("Successfully initialized SystemFactory");
    }
}
