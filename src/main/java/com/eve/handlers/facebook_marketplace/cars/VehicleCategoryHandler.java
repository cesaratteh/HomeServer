package com.eve.handlers.facebook_marketplace.cars;

import com.eve.config.Logger;
import com.eve.handlers.facebook_marketplace.CategoryHandler;
import com.eve.notifier.Notifier;
import com.eve.util.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class VehicleCategoryHandler implements CategoryHandler {

    private final static Logger LOGGER = Logger.getLogger(VehicleCategoryHandler.class);

    private final static String VEHICLE_QUERY_URL =
            "https://www.facebook.com/marketplace/seattle/vehicles?sortBy=creation_time_descend";

    public List<CarPrice> CarQueries =
            Arrays.asList(
//                    new CarPrice(20_000, "2022", "Honda", "accord"),
//                    new CarPrice(19_000, "2021", "Honda", "accord"),
//                    new CarPrice(19_000, "2020", "Honda", "accord"),
//                    new CarPrice(40_000, "2013"),
//                    new CarPrice(40_000, "2015", "honda")
            );
    public record CarPrice(int price, String... titleKeywords) {}

    private final Notifier notifier;

    public VehicleCategoryHandler(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void processItem(WebElement carElement) {
        try {
            Car car = new Car(carElement);
            LOGGER.log(car.toString());

            if (car.isGoodDeal()) {
                notifier.notify(
                        car.name,
                        car.price + " " + car.miles,
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

        boolean isGoodDeal() {
            return isSub3500() || isGoodModel();
        }

        private boolean isSub3500() {
            if (this.price < 3500 && this.price > 1000) {
                return IntStream.rangeClosed(2013, 2023)
                        .anyMatch(year -> this.name.contains(String.valueOf(year)));
            }

            return false;
        }

        private boolean isGoodModel() {
            return CarQueries.stream().anyMatch(query ->
                    this.price < query.price() &&
                            Arrays.stream(query.titleKeywords).allMatch(keyword ->
                                    this.name.toLowerCase().contains(keyword.toLowerCase()))
            );
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
