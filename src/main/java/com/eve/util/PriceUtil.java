package com.eve.util;

public class PriceUtil {

    public static int toInt(String price) {
        String cleanPriceString = price.replace("$", "").replace(",", "").trim();
        return Integer.parseInt(cleanPriceString);
    }
}
