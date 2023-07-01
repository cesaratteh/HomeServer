package com.eve.util;

public class Wait {
    public static void performActionThenWait(Runnable action, long seconds) {
        try {
            action.run();
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Logger.error("Interrupted while performing action", e);
        }
    }

    public static void waitThenPerformAction(Runnable action, long seconds) {
        try {
            Thread.sleep(seconds * 1000);
            action.run();
        } catch (InterruptedException e) {
            Logger.error("Interrupted while performing action", e);
        }
    }
}
