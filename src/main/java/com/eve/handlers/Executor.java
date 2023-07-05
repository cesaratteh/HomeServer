package com.eve.handlers;

import com.eve.config.AppConfig;
import com.eve.config.LoggerFactory;
import com.eve.config.RunnableFactory;
import com.eve.util.Wait;

import java.util.HashMap;
import java.util.concurrent.*;

public class Executor {

    private final static Long POLL_FOR_DEAD_THREADS_AND_RERUN_EVERY_X_MS =
            AppConfig.EXECUTOR_POLL_FOR_DEAD_THREADS_AND_RERUN_EVERY_X_MS;

    private final ExecutorService threadPool;
    private final RunnableFactory[] runnableFactories;
    private final HashMap<RunnableFactory, Future<?>> activeRunnables;

    public Executor(
            ExecutorService threadPool,
            RunnableFactory... runnableFactories) {
        this.threadPool = threadPool;
        this.runnableFactories = runnableFactories;
        this.activeRunnables = new HashMap<>();
    }

    public void start() {
        LoggerFactory.getLogger(this.getClass()).info("Running runnables");

        for (RunnableFactory factory : runnableFactories) createRunnableAndRun(factory);

        while (true) {
            Wait.waitThenPerformAction(
                    () -> rerunDeadRunnables(activeRunnables),
                    POLL_FOR_DEAD_THREADS_AND_RERUN_EVERY_X_MS
            );
        }
    }

    private void rerunDeadRunnables(HashMap<RunnableFactory, Future<?>> activeRunnables) {
        activeRunnables.forEach((factory, future) -> {
            if (future.isDone()) {
                LoggerFactory.getLogger(Executor.class).error(
                        "Found a dead runnable, restarting " + factory, RuntimeException::new);
                createRunnableAndRun(factory);
            }
        });
    }

    private void createRunnableAndRun(RunnableFactory runnableFactory) {
        Future<?> submit = this.threadPool.submit(runnableFactory.create());
        activeRunnables.put(runnableFactory, submit);
    }
}
