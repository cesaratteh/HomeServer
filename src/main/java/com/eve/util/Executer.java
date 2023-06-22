package com.eve.util;

import com.eve.util.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class Executer {

    private static final int INTERVAL_SECONDS = 10;

    public Executer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnce();
            }
        };
        timer.scheduleAtFixedRate(task, 0, INTERVAL_SECONDS * 1000);

        Logger.log("Successfully initialized the executor");
    }

    private static void runOnce() {
        Logger.log("running handlers");
    }
}
