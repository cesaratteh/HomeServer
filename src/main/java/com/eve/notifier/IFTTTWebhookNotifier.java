package com.eve.notifier;

import com.eve.config.AppConfig;
import com.eve.config.Logger;
import com.eve.util.Wait;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IFTTTWebhookNotifier implements Notifier {

    private final static Logger logger = Logger.getLogger(IFTTTWebhookNotifier.class);

    private final String EVENT_NAME = AppConfig.IFTTT_WEBHOOK_NOTIFIER_EVENT_NAME;
    private final String IFTTT_API_KEY = AppConfig.IFTTT_WEBHOOK_NOTIFIER_IFTTT_API_KEY;
    private final long NOTIFY_DELAY_MS = AppConfig.IFTTT_WEBHOOK_NOTIFIER_NOTIFY_DELAY_MS;

    private static int makePostRequest(final String url, final String jsonBody) {
        int responseCode = -1;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }

            responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return responseCode;
    }

    @Override
    public void notify(final String title, final String body, final String url) {
        try {
            Wait.performActionThenWait(
                    () -> {
                        int responseCode = makePostRequest("https://maker.ifttt.com/trigger/" + EVENT_NAME + "/with/key/" + IFTTT_API_KEY,
                                "{\"value1\": \"" + title + "\", \"value2\": \"" + body + "\", \"value3\": \"" + url + "\"}");

                        logger.log("IFTTTWebhookNotifier: Sending notification succeeded " +
                                responseCode);
                    }, NOTIFY_DELAY_MS
            );
        } catch (Exception e) {
            logger.error("IFTTTWebhookNotifier failed to notify ", e);
        }
    }
}
