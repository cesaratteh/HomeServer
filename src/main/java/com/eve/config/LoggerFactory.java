package com.eve.config;

import org.apache.logging.log4j.LogManager;

public class LoggerFactory {
    public static org.apache.logging.log4j.Logger getLogger(Class clazz) {
        return LogManager.getLogger(clazz);
    }
}
