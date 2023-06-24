package com.eve.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumDriverFactory {

    private static final boolean HEADLESS_MODE = AppConfig.SELENIUM_DRIVER_FACTORY_HEADLESS_MODE;
    private final ChromeOptions options;

    public SeleniumDriverFactory() {
        this.options = new ChromeOptions();
        if (HEADLESS_MODE) {
            this.options.addArguments("--headless");
        }

        WebDriverManager.chromedriver().setup();
    }

    public WebDriver newDriver() {
        return new ChromeDriver(options);
    }
}
