package com.eve.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logger {
    private static final String LOG_FILE_PATH = "HomeServer";

    private static java.util.logging.Logger logger;

    static {
        try {
            LogManager.getLogManager().reset();

            String logFilePath = LOG_FILE_PATH + "log_" + getTimestamp() + ".log";
            FileHandler fileHandler = new FileHandler(logFilePath);
            fileHandler.setFormatter(new SimpleFormatter());

            logger = java.util.logging.Logger.getLogger(Logger.class.getName());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        logger.info(message);
    }

    private static String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date());
    }
}
