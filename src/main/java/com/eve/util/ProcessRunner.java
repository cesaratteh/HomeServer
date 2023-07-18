package com.eve.util;

import com.eve.config.Logger;

import java.io.IOException;

public class ProcessRunner {

    private final static Logger logger = Logger.getLogger(ProcessRunner.class);

    public static int runSync(String command) throws InterruptedException, IOException {
        Process process = Runtime.getRuntime().exec(command.split(" "));
        int exitCode = process.waitFor();

        logger.log("Executed " + command + "; Exit code " + exitCode + " Console output: ");
        return exitCode;
    }
}
