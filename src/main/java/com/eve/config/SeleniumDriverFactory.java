package com.eve.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumDriverFactory {

    private static final boolean HEADLESS_MODE = AppConfig.SELENIUM_DRIVER_FACTORY_HEADLESS_MODE;
    private static final long DRIVER_DELAY_MILLIS = AppConfig.SELENIUM_DRIVER_DELAY_IN_MILLIS;

    private final ChromeOptions options;

    public SeleniumDriverFactory() {
        this.options = new ChromeOptions();
        if (HEADLESS_MODE) {
            this.options.addArguments("--headless");
        }

        WebDriverManager.chromedriver().setup();
    }

    public WebDriver newDriver() {
        return new FixedDelayChromeDriver(options);
    }

    class FixedDelayChromeDriver extends ChromeDriver {

        public FixedDelayChromeDriver(ChromeOptions options) {
            super(options);
        }

        @Override
        public void get(String url) {
            super.get(url);
            try {
                Thread.sleep(DRIVER_DELAY_MILLIS);
            } catch (InterruptedException e) {
                Logger.error("Interrupted while delaying WebDriver get ", e);
            }
        }
    }
}
