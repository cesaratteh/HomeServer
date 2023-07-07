package com.eve.handlers;


import com.eve.config.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class Executor {

    private final ExecutorService threadPool;
    private final Runnable[] runnables;

    public Executor(
            ExecutorService threadPool,
            Runnable... runnables) {
        this.threadPool = threadPool;
        this.runnables = runnables;
    }

    public void start() {
        LoggerFactory.getLogger(this.getClass()).info("Running handlers");

        for (Runnable runnable : runnables) {
            threadPool.execute(runnable);
        }

        threadPool.shutdown();
        LoggerFactory.getLogger(this.getClass()).info("Successfully initialized the executor");
    }
}
