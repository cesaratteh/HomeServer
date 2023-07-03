package com.eve.config;

import org.apache.commons.cli.*;

public class CommandLineArgumentsConfig {

    private final Options options;
    private Arguments arguments;

    public CommandLineArgumentsConfig(String[] args) {
        this.options = new Options();

        options.addOption(Option.builder("fromEmail").required().hasArg().desc("From email address").build());
        options.addOption(Option.builder("password").required().hasArg().desc("Email password").build());
        options.addOption(Option.builder("toEmail").required().hasArg().desc("To email address").build());

        try {
            CommandLine cmd = new DefaultParser().parse(options, args);

            this.arguments = new Arguments(
                    cmd.getOptionValue("fromEmail"),
                    cmd.getOptionValue("password"),
                    cmd.getOptionValue("toEmail")
            );

            LoggerFactory.getLogger(this.getClass()).info(this.arguments.toString());
        } catch (ParseException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            System.exit(1);
        }
    }

    public Arguments getArguments() {
        return arguments;
    }

    class Arguments {
        String fromEmail;
        String password;
        String toEmail;

        public Arguments(String fromEmail, String password, String toEmail) {
            this.fromEmail = fromEmail;
            this.password = password;
            this.toEmail = toEmail;
        }

        @Override
        public String toString() {
            return "Arguments{" +
                    "fromEmail='" + fromEmail + '\'' +
                    ", password='" + password + '\'' +
                    ", toEmail='" + toEmail + '\'' +
                    '}';
        }
    }
}
