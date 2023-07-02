package com.eve.util;

import com.eve.config.Logger;

public class Wait {
    public static void performActionThenWait(Runnable action, long millis) {
        try {
            action.run();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.error("Interrupted while performing action", e);
        }
    }

    public static void waitThenPerformAction(Runnable action, long millis) {
        try {
            Thread.sleep(millis);
            action.run();
        } catch (InterruptedException e) {
            Logger.error("Interrupted while performing action", e);
        }
    }
}
