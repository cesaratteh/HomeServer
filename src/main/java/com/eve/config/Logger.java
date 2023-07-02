package com.eve.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

public class Logger {
    private static final String LOG_FILE_PATH = AppConfig.LOGGER_LOG_FILE;
    private static final long LOG_FILE_ROTATE_EVERY_X_HOURS = AppConfig.LOGGER_LOG_FILE_ROTATE_EVERY_X_MS;
    private static final boolean ENABLE_SYSTEM_OUT = AppConfig.LOGGER_ENABLE_SYSTEM_OUT;

    private static long lastUpdateTime = System.currentTimeMillis();

    private static java.util.logging.Logger logger;

    static {
        reset();
    }

    private static void reset() {
        try {
            LogManager.getLogManager().reset();

            String logFilePath = LOG_FILE_PATH + getTimestamp() + ".log";
            FileHandler fileHandler = new FileHandler(logFilePath);
            fileHandler.setFormatter(new SimpleFormatter());

            logger = java.util.logging.Logger.getLogger(Logger.class.getName());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean shouldRotateLogFile() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastUpdateTime >= LOG_FILE_ROTATE_EVERY_X_HOURS * 60 * 60 * 1000;
    }

    public static void log(String message) {
        if (shouldRotateLogFile()) {
            reset();
            lastUpdateTime = System.currentTimeMillis();
        }

        logger.info(message);
        if (ENABLE_SYSTEM_OUT) {
            System.out.println(message);
        }
    }

    public static void error(String message, Throwable t) {
        if (shouldRotateLogFile()) {
            reset();
            lastUpdateTime = System.currentTimeMillis();
        }

        logger.log(Level.SEVERE, message, t);
        if (ENABLE_SYSTEM_OUT) {
            System.out.println(message);
            t.printStackTrace();
        }
    }

    private static String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return dateFormat.format(new Date());
    }
}
