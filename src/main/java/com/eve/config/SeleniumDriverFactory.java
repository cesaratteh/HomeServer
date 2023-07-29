package com.eve.config;

import com.eve.util.Wait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Random;

public class SeleniumDriverFactory {

    private static final boolean HEADLESS_MODE = AppConfig.SELENIUM_DRIVER_FACTORY_HEADLESS_MODE;
    private static final long DRIVER_MIN_DELAY_MILLIS = AppConfig.SELENIUM_DRIVER_MIN_DELAY_IN_MILLIS;
    private static final long DRIVER_MAX_DELAY_MILLIS = AppConfig.SELENIUM_DRIVER_MAX_DELAY_IN_MILLIS;
    private static final Random RANDOM = new Random();

    private final ChromeOptions options;

    public SeleniumDriverFactory() {
        this.options = new ChromeOptions();
        if (HEADLESS_MODE) {
            this.options.addArguments("--headless");
        }

        WebDriverManager.chromedriver().setup();
    }

    public WebDriver newDriver() {
        return new FuzzyDelayChromeDriver(options);
    }

    class FuzzyDelayChromeDriver extends ChromeDriver {

        public FuzzyDelayChromeDriver(ChromeOptions options) {
            super(options);
        }

        @Override
        public void get(String url) {
            long delay = DRIVER_MIN_DELAY_MILLIS;
            delay += (DRIVER_MAX_DELAY_MILLIS - DRIVER_MIN_DELAY_MILLIS) * RANDOM.nextDouble();

            Wait.performActionThenWait(() -> super.get(url), delay);
        }
    }
}
