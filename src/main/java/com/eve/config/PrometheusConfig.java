package com.eve.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PrometheusConfig {

    private static final int SERVER_PORT = AppConfig.PROMETHEUS_CONFIG_CLIENT_SERVER_PORT;
    private static final String METRICS_NAMESPACE = AppConfig.PROMETHEUS_CONFIG_METRICS_NAMESPACE;

    private static CollectorRegistry registry;
    private static HTTPServer server;

    public static void init() throws IOException {
        registry = new CollectorRegistry();
        server = new HTTPServer(new InetSocketAddress(SERVER_PORT), registry);
    }

    public static Counter counter(String subsystem, String name, String help) {
        Counter result = Counter.build()
                .namespace(METRICS_NAMESPACE)
                .subsystem(subsystem)
                .name(name)
                .help(help)
                .register();
        registry.register(result);
        return result;
    }

    public static void shutdown() {
        server.stop();
    }
}
