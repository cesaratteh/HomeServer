package com.eve.config;

import com.eve.util.ProcessRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Docker {

    private final static Path DOCKER_COMPOSE_FILE_PATH =
            Paths.get(AppConfig.EXTERNAL_CONFIG_ABSOLUTE_PATH.toString(), AppConfig.DOCKER_CONFIG_DOCKER_COMPOSE_FILE);
    private final static String DOCKER_COMPOSE_PROJECT_NAME = AppConfig.DOCKER_CONFIG_DOCKER_COMPOSE_PROJECT_NAME;

    public static void up() throws IOException, InterruptedException {
        ProcessRunner.runSync(String.format("docker-compose -p %s -f %s up -d",
                DOCKER_COMPOSE_PROJECT_NAME, DOCKER_COMPOSE_FILE_PATH));
    }

    public static void down() throws IOException, InterruptedException {
        ProcessRunner.runSync(String.format("docker-compose -p %s down",
                DOCKER_COMPOSE_PROJECT_NAME));
    }
}
