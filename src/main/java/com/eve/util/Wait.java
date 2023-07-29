package com.eve.util;

import com.eve.config.Logger;

public class Wait {

    private final static Logger logger = Logger.getLogger(Wait.class);

    public static void performActionThenWait(Runnable action, long millis) {
        action.run();
        sleep(millis);
    }

    public static void waitThenPerformAction(Runnable action, long millis) {
        sleep(millis);
        action.run();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("Interrupted while performing action", e);
        }
    }
}
