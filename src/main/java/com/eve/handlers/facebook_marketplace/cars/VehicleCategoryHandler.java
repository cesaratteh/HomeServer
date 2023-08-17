package com.eve.handlers.facebook_marketplace.cars;

import com.eve.config.Logger;
import com.eve.handlers.facebook_marketplace.CategoryHandler;
import com.eve.notifier.Notifier;
import com.eve.util.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleCategoryHandler implements CategoryHandler {

    private final static Logger LOGGER = Logger.getLogger(VehicleCategoryHandler.class);

    private final static String VEHICLE_QUERY_URL =
            "https://www.facebook.com/marketplace/seattle/vehicles?sortBy=creation_time_descend";

    private final Notifier notifier;

    public VehicleCategoryHandler(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void processItem(WebElement carElement) {
        try {
            Car car = new Car(carElement);
            LOGGER.log(car.toString());

            if (car.price < 3500 && car.price > 1000) {
                notifier.notify(
                        car.name + " " + car.price + " " + car.miles,
                        VehicleCategoryHandler.class.getSimpleName() + " Found a Car Under $3,500",
                        car.url);
            }
        } catch (Exception e) {
            LOGGER.error("VehicleCategoryHandler failed to process an item ", e);
        }
    }

    @Override
    public String getCategoryURL() {
        return VEHICLE_QUERY_URL;
    }

    private class Car {
        String name;
        int price;
        String location;
        Optional<String> miles;
        String url;

        public Car(WebElement carElement) {
            String text = carElement.getText();

            String[] lines = text.split("\n");
            String priceLine = lines[0];
            String carLine = lines[1];
            String locationLine = lines[2];
            Optional<String> milesLine = lines.length > 3 ? Optional.of(lines[3]) : Optional.empty();

            this.name = carLine;
            this.price = PriceUtil.toInt(priceLine);
            this.location = locationLine;
            this.miles = milesLine;

            String url = carElement.findElement(By.tagName("a")).getAttribute("href");
            Matcher matcher = Pattern.compile(MARKETPLACE_ITEM_URL_REGEX).matcher(url);
            this.url = matcher.find() ? matcher.group() : null;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", miles='" + miles + '\'' +
                    ", location='" + location + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
