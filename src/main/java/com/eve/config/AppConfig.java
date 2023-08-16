package com.eve.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class AppConfig {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String PWD = System.getProperty("user.dir");
    public static final String CONFIG_EXTRACTOR_RESOURCES_CONFIG_DIR_NAME = "config";
    public static final Path EXTERNAL_CONFIG_ABSOLUTE_PATH = Paths.get(PWD, CONFIG_EXTRACTOR_RESOURCES_CONFIG_DIR_NAME);

    public static final Long MAIN_SERVICE_CRASH_RESTART_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(10);
    public static final int SYSTEM_FACTORY_EXECUTOR_THREAD_POOL_SIZE = 5;
    public static final Long EXECUTOR_POLL_FOR_DEAD_THREADS_AND_RERUN_EVERY_X_MS = TimeUnit.MINUTES.toMillis(5);

    public static final String DOCKER_CONFIG_DOCKER_COMPOSE_FILE = "docker-compose.yml";
    public static final String DOCKER_CONFIG_DOCKER_COMPOSE_PROJECT_NAME = "home-server";

    public static final String PROMETHEUS_CONFIG_METRICS_NAMESPACE = "HomeServer";
    public static final int PROMETHEUS_CONFIG_CLIENT_SERVER_PORT = 9093;

    public static final String SQLITE_DAO_DB_FILE = "database.db";

    public static final boolean SELENIUM_DRIVER_FACTORY_HEADLESS_MODE = false;
    public static final long SELENIUM_DRIVER_MIN_DELAY_IN_MILLIS = TimeUnit.SECONDS.toMillis(7);
    public static final long SELENIUM_DRIVER_MAX_DELAY_IN_MILLIS = TimeUnit.SECONDS.toMillis(12);

    public final static String IFTTT_WEBHOOK_NOTIFIER_IFTTT_API_KEY = "bKQdb9G-C3bN2KUqU6S2cM";
    public final static String IFTTT_WEBHOOK_NOTIFIER_EVENT_NAME = "home_server_notification";
    public final static long IFTTT_WEBHOOK_NOTIFIER_NOTIFY_DELAY_MS = TimeUnit.SECONDS.toMillis(2);

    public static final String BIZ_BUY_SELL_RUNNABLE_NATIONWIDE_3DAYS_QUERY_URL =
            "https://www.bizbuysell.com/businesses-for-sale/?q=ZGxhPTM%3D";
    public static final long BIZ_BUY_SELL_FETCH_NEW_LISTINGS_EVERY_X_MS = TimeUnit.DAYS.toMillis(1);
    public static final long BIZ_BUY_SELL_CHECK_LISTINGS_STILL_UP_EVERY_X_MS = TimeUnit.DAYS.toMillis(3);

    public static final long FACEBOOK_MARKETPLACE_CAR_RUNNABLE_RUN_EVERY_MS = TimeUnit.HOURS.toMillis(1);
}
