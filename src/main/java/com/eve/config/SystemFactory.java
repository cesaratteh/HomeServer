package com.eve.config;

import com.eve.util.Executer;
import com.eve.util.Logger;
import org.openqa.selenium.WebDriver;

public class SystemFactory {

    public static void initialize() {
        SeleniumConfig seleniumConfig = new SeleniumConfig();
        WebDriver driver = seleniumConfig.getSeleniumDriver();
        driver.get("https://www.facebook.com/marketplace/sanfrancisco/vehicles?sortBy=creation_time_descend");

        Executer executer = new Executer();

        Logger.log("Successfully initialized SystemFactory");
    }
}
