package com.eve;

import com.eve.config.AppConfig;
import com.eve.config.Logger;
import com.eve.config.SystemFactory;

public class Main {

    private final static Logger logger = Logger.getLogger(Main.class);

    private static final long SERVICE_CRASH_RESTART_TIMEOUT_MS = AppConfig.MAIN_SERVICE_CRASH_RESTART_TIMEOUT_MS;

    public static void main(String[] args) {
        rerunIfCrashed(() -> SystemFactory.initialize(args), SERVICE_CRASH_RESTART_TIMEOUT_MS);
        logger.log("Service Initialized successfully");
    }

    private static void rerunIfCrashed(Runnable task, long retryTimerMS) {
        while (true) {
            try {
                task.run();
                break;
            } catch (Throwable e) {
                logger.error("Service crashed with exceptions ", e);

                try {
                    Thread.sleep(retryTimerMS);
                } catch (InterruptedException ex) {
                    logger.error("Thread.sleep threw an exception ", e);
                }
            }
        }
    }
}
