package com.eve.notifier;

import java.util.HashSet;
import java.util.Set;

public class IFTTTDedupingNotifier extends IFTTTWebhookNotifier {

    private static final Set<String> URLS = new HashSet<>();

    @Override
    public void notify(String title, String body, String url) {
        if (URLS.add(url)) {
            super.notify(title, body, url);
        }
    }
}
