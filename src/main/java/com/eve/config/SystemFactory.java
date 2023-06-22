package com.eve.config;

import com.eve.handlers.Executor;

import java.util.concurrent.Executors;

public class SystemFactory {

    private static final boolean HEADLESS_MODE = false;
    private static final int EXECUTOR_THREAD_POOL_SIZE = 5;

    public static void initialize() {
        Executor executor = new Executor(
                Executors.newFixedThreadPool(EXECUTOR_THREAD_POOL_SIZE),
                new SeleniumDriverBuilder(HEADLESS_MODE));
        executor.start();

        Logger.log("Successfully initialized SystemFactory");
    }
}
