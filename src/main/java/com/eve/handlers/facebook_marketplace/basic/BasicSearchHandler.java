package com.eve.handlers.facebook_marketplace.basic;

import com.eve.config.Logger;
import com.eve.handlers.facebook_marketplace.CategoryHandler;
import com.eve.notifier.Notifier;
import com.eve.util.PriceUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicSearchHandler implements CategoryHandler {

    private final static Logger LOGGER = Logger.getLogger(BasicSearchHandler.class);

    private int index;
    private final Notifier notifier;

    public BasicSearchHandler(Notifier notifier) {
        this.index = -1;
        this.notifier = notifier;
    }

    @Override
    public void processItem(WebElement carElement) {
        BasicSearchQueries.Query query = BasicSearchQueries.QUERIES.get(index);

        try {
            Item item = new Item(carElement);
            LOGGER.log(item.toString());

            if (item.matchesQuery(query)) {
                LOGGER.log("MATCHED!!!!! cesar");
                notifier.notify(
                        String.join(" ", query.titleKeywords()),
                        "$" + item.price + " ",
                        item.url);
            }
        } catch (Exception e) {
            LOGGER.error("BasicSearchHandler failed to process an item ", e);
        }
    }

    @Override
    public String getCategoryURL() {
        incIndex();
        return BasicSearchQueries.QUERIES.get(index).URL();
    }

    private static class Item {
        int price;
        String text;
        String url;

        public Item(WebElement element) {
            String text = element.getText();
            this.text = text;

            String[] lines = text.split("\n");
            String priceLine = lines[0];

            this.price = PriceUtil.toInt(priceLine);

            String url = element.findElement(By.tagName("a")).getAttribute("href");
            Matcher matcher = Pattern.compile(MARKETPLACE_ITEM_URL_REGEX).matcher(url);
            this.url = matcher.find() ? matcher.group() : null;
        }

        public boolean matchesQuery(BasicSearchQueries.Query query) {
            if (this.price < query.maxPrice() &&
                    this.price > query.minPrice() &&
                    this.text.toLowerCase().contains(query.location().toLowerCase())) {
                return Arrays.stream(query.titleKeywords()).allMatch(keyword ->
                        text.toLowerCase().contains(keyword.toLowerCase()));
            }

            return false;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "text='" + text + '\'' +
                    ", price='" + price + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    private void incIndex() {
        index++;

        if (index >= BasicSearchQueries.QUERIES.size()) {
            index = 0;
        }
    }
}
