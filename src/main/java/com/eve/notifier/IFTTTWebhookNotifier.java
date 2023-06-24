package com.eve.notifier;

import com.eve.config.AppConfig;
import com.eve.util.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IFTTTWebhookNotifier implements Notifier {

    private final String EVENT_NAME = AppConfig.IFTTT_WEBHOOK_NOTIFIER_EVENT_NAME;
    private final String IFTTT_API_KEY = AppConfig.IFTTT_WEBHOOK_NOTIFIER_IFTTT_API_KEY;

    private static int makePostRequest(final String url, final String jsonBody) throws IOException {
        // Create a HttpURLConnection object
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        connection.disconnect();

        return responseCode;
    }

    @Override
    public void notify(final String title, final String body, final String url) {
        try {
            int responseCode = makePostRequest("https://maker.ifttt.com/trigger/" + EVENT_NAME + "/with/key/" + IFTTT_API_KEY,
                    "{\"value1\": \"" + title + "\", \"value2\": \"" + body + "\", \"value3\": \"" + url + "\"}");

            System.out.println("IFTTTWebhookNotifier: Sending notification succeeded " +
                    responseCode);
        } catch (Exception e) {
            Logger.error("IFTTTWebhookNotifier failed to notify ", e);
        }
    }
}
