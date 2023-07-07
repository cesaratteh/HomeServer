package com.eve.config;

import java.util.concurrent.TimeUnit;

public class AppConfig {
    public static final String SQLITE_DAO_DB_FILE = "database.db";

    public static final Long MAIN_SERVICE_CRASH_RESTART_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(10);

    public static final Long EXECUTOR_POLL_FOR_DEAD_THREADS_AND_RERUN_EVERY_X_MS = TimeUnit.MINUTES.toMillis(5);

    public static final boolean SELENIUM_DRIVER_FACTORY_HEADLESS_MODE = false;
    public static final long SELENIUM_DRIVER_DELAY_IN_MILLIS = TimeUnit.SECONDS.toMillis(5);

    public static final int SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE = 5;

    public static final String BIZ_BUY_SELL_RUNNABLE_NATIONWIDE_3DAYS_QUERY_URL =
            "https://www.bizbuysell.com/businesses-for-sale/?q=ZGxhPTM%3D";
    public static final long BIZ_BUY_SELL_FETCH_NEW_LISTINGS_EVERY_X_MS = TimeUnit.MINUTES.toMillis(1);
    public static final long BIZ_BUY_SELL_CHECK_LISTINGS_STILL_UP_EVERY_X_MS = TimeUnit.DAYS.toMillis(1);

    public final static String IFTTT_WEBHOOK_NOTIFIER_IFTTT_API_KEY = "bKQdb9G-C3bN2KUqU6S2cM";
    public final static String IFTTT_WEBHOOK_NOTIFIER_EVENT_NAME = "home_server_notification";

    public static String EMAIL_NOTIFIER_FROM_EMAIL;
    public static String EMAIL_NOTIFIER_FROM_EMAIL_PASSWORD;
    public static String EMAIL_NOTIFIER_TO_EMAIL;
}
