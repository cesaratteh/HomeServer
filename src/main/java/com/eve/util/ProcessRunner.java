package com.eve.util;

import com.eve.config.LoggerFactory;

import java.io.IOException;

public class ProcessRunner {

    public static int runSync(String command) throws InterruptedException, IOException {
        Process process = Runtime.getRuntime().exec(command.split(" "));
        int exitCode = process.waitFor();

        LoggerFactory.getLogger(ProcessRunner.class)
                .info("Executed " + command + "; Exit code " + exitCode +
                        " Console output: ");
        return exitCode;
    }
}
