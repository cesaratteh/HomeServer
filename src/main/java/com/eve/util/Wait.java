package com.eve.util;

import com.eve.config.LoggerFactory;

public class Wait {
    public static void performActionThenWait(Runnable action, long millis) {
        try {
            action.run();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LoggerFactory.getLogger(Wait.class).error("Interrupted while performing action", e);
        }
    }

    public static void waitThenPerformAction(Runnable action, long millis) {
        try {
            Thread.sleep(millis);
            action.run();
        } catch (InterruptedException e) {
            LoggerFactory.getLogger(Wait.class).error("Interrupted while performing action", e);
        }
    }
}
