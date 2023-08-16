package com.eve.handlers.facebook_marketplace;

import org.openqa.selenium.WebElement;

public interface CategoryHandler {

    // Regex used to extract marketplace item url.
    // Example - https://www.facebook.com/marketplace/item/582385194079531
    String MARKETPLACE_ITEM_URL_REGEX =
            "https://www\\.facebook\\.com/marketplace/item/[0-9]+";
    void processItem(WebElement carElement);

    String getCategoryURL();
}
