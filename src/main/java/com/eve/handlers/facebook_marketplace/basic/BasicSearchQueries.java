package com.eve.handlers.facebook_marketplace.basic;

import java.util.Arrays;
import java.util.List;

public class BasicSearchQueries {

    public static List<Query> QUERIES = Arrays.asList(
            new Query("https://www.facebook.com/marketplace/seattle/search?minPrice=20&maxPrice=150&deliveryMethod=local_pick_up&sortBy=price_ascend&query=steelcase%20chair",
                    20, 101, "Seattle", "steelcase"),
            new Query("https://www.facebook.com/marketplace/seattle/search?minPrice=20&maxPrice=150&deliveryMethod=local_pick_up&sortBy=price_ascend&query=herman%20miller%20chair",
                              20, 101, "Seattle", "herman", "miller")
    );


    public record Query(String URL, int minPrice, int maxPrice, String location, String... titleKeywords) {}
}
