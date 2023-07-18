package com.eve.util;

import com.eve.config.Logger;

public class Wait {

    private final static Logger logger = Logger.getLogger(Wait.class);

    public static void performActionThenWait(Runnable action, long millis) {
        try {
            action.run();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("Interrupted while performing action", e);
        }
    }

    public static void waitThenPerformAction(Runnable action, long millis) {
        try {
            Thread.sleep(millis);
            action.run();
        } catch (InterruptedException e) {
            logger.error("Interrupted while performing action", e);
        }
    }
}
