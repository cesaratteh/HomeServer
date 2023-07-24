package com.eve.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Enumeration;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Prometheus {

    private static final int SERVER_PORT = AppConfig.PROMETHEUS_CONFIG_CLIENT_SERVER_PORT;
    private static final String METRICS_NAMESPACE = AppConfig.PROMETHEUS_CONFIG_METRICS_NAMESPACE;

    private static CollectorRegistry registry;
    private static HTTPServer server;

    public static void init() throws IOException {
        registry = new CollectorRegistry();
        server = new HTTPServer(new InetSocketAddress(SERVER_PORT), registry);
    }

    public static Gauge gauge(Class subsystem, String name) {
        Gauge result = Gauge.build()
                .namespace(METRICS_NAMESPACE)
                .subsystem(subsystem.getSimpleName())
                .name(name)
                .help("good luck")
                .register();
        registry.register(result);
        return result;
    }

    public static Counter counter(Class subsystem, String name) {
        Counter result = Counter.build()
                .namespace(METRICS_NAMESPACE)
                .subsystem(subsystem.getSimpleName())
                .name(name)
                .help("good luck")
                .register();
        registry.register(result);
        return result;
    }

    public static Enumeration enumeration(Class subsystem, String name, Class states) {
        Enumeration result = Enumeration.build()
                .namespace(METRICS_NAMESPACE)
                .subsystem(subsystem.getSimpleName())
                .name(name)
                .states(states)
                .help("good luck")
                .register();
        registry.register(result);
        return result;
    }

    public static void shutdown() {
        server.close();
    }
}
