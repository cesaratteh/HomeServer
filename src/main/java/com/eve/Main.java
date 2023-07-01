package com.eve;

import com.eve.config.AppConfig;
import com.eve.config.SystemFactory;
import com.eve.util.Logger;

public class Main {

    private static final long SERVICE_CRASH_RESTART_TIMEOUT_IN_MINUTES = AppConfig.MAIN_SERVICE_CRASH_RESTART_TIMEOUT_IN_MINUTES;

    public static void main(String[] args) {
        rerunIfCrashed(() -> SystemFactory.initialize(args), SERVICE_CRASH_RESTART_TIMEOUT_IN_MINUTES);
        Logger.log("Service Initialized successfully");
    }

    private static void rerunIfCrashed(Runnable task, long retryTimerInMinutes) {
        while (true) {
            try {
                task.run();
                break;
            } catch (Throwable e) {
                Logger.error("Service crashed with exceptions ", e);

                try {
                    Thread.sleep(retryTimerInMinutes * 60 * 1000);
                } catch (InterruptedException ex) {
                    Logger.error("Thread.sleep threw an exception ", e);
                }
            }
        }
    }

}
