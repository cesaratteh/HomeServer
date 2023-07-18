package com.eve.util;

import com.eve.config.AppConfig;
import com.eve.config.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ProcessRunner {

    private final static Logger logger = Logger.getLogger(ProcessRunner.class);

    private static final String lineSeparator = AppConfig.LINE_SEPARATOR;

    public static int runSync(String command) throws InterruptedException, IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        InputStream inputStream = process.getInputStream();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                output.append(scanner.nextLine());
                output.append(lineSeparator);
            }
        }

        int exitCode = process.waitFor();

        String logMessage = "Executed " + command +
                "; Exit code " + exitCode + " Console output: " + output;
        if (exitCode == 0) {
            logger.log(logMessage);
        } else {
            logger.error(logMessage);
        }

        return exitCode;
    }
}
