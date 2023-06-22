package com.eve.handlers;

import com.eve.config.Logger;
import com.eve.config.SeleniumDriverBuilder;

import java.util.concurrent.ExecutorService;

public class Executor {

    ExecutorService threadPool;
    SeleniumDriverBuilder seleniumDriverBuilder;

    public Executor(ExecutorService threadPool, SeleniumDriverBuilder seleniumDriverBuilder) {
        this.threadPool = threadPool;
        this.seleniumDriverBuilder = seleniumDriverBuilder;
    }

    public void start() {
        Logger.log("running handlers");
        threadPool.execute(new FacebookMarketplaceCarRunnable(seleniumDriverBuilder.newDriver()));

        threadPool.shutdown();
        Logger.log("Successfully initialized the executor");
    }
}
