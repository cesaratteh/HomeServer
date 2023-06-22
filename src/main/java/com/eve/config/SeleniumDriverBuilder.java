package com.eve.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumDriverBuilder {

    private final ChromeOptions options;
    public SeleniumDriverBuilder(boolean headlessMode) {
        this.options = new ChromeOptions();
        if (headlessMode) {
            this.options.addArguments("--headless");
        }

        WebDriverManager.chromedriver().setup();
    }

    public WebDriver newDriver() {
        return new ChromeDriver(options);
    }
}
