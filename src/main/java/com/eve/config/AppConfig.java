package com.eve.config;

public class AppConfig {
    public static final String LOGGER_LOG_FILE = "HomeServerLog_";

    public static final Long MAIN_SERVICE_CRASH_RESTART_TIMEOUT_IN_MINUTES = 5L;

    public static final boolean SELENIUM_DRIVER_FACTORY_HEADLESS_MODE = false;
    public static final int SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE = 5;

    public final static String IFTTT_WEBHOOK_NOTIFIER_IFTTT_API_KEY = "bKQdb9G-C3bN2KUqU6S2cM";
    public final static String IFTTT_WEBHOOK_NOTIFIER_EVENT_NAME = "home_server_notification";

    public static String EMAIL_NOTIFIER_FROM_EMAIL;
    public static String EMAIL_NOTIFIER_FROM_EMAIL_PASSWORD;
    public static String EMAIL_NOTIFIER_TO_EMAIL;
}
