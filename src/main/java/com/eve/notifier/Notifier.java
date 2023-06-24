package com.eve.notifier;

public interface Notifier {
    void notify(final String title, final String body, final String url);
}
