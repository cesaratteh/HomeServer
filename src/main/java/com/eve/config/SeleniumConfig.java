package com.eve.config;

import com.eve.util.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumConfig {

    private final WebDriver driver;

    public SeleniumConfig() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();

        Logger.log("Successfully initialized Selenium");
    }

    public WebDriver getSeleniumDriver() {
        return this.driver;
    }
}
