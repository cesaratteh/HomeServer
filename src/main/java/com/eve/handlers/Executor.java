package com.eve.handlers;

import com.eve.util.Logger;

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
        Logger.log("Running handlers");

        for (Runnable runnable : runnables) {
            threadPool.execute(runnable);
        }

        threadPool.shutdown();
        Logger.log("Successfully initialized the executor");
    }
}
