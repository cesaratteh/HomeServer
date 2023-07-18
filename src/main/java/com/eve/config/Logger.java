package com.eve.config;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private final org.apache.logging.log4j.Logger actualLogger;

    private Logger(org.apache.logging.log4j.Logger actualLogger) {
        this.actualLogger = actualLogger;
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(LogManager.getLogger(clazz));
    }

    public void log(String message) {
        actualLogger.info(message);
    }

    public void error(String message) {
        actualLogger.error(message);
    }

    public void error(String message, Throwable e) {
        actualLogger.error(message, e);
    }
}
