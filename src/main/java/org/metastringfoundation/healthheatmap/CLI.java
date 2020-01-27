package org.metastringfoundation.healthheatmap;

import org.apache.commons.cli.*;

public class CLI {
    //TODO convert the other builder in this project to static class
    final static Option datasetName = Option.builder("n")
            .hasArg()
            .longOpt("name")
            .build();
    final static Option path = Option.builder("p")
            .hasArg()
            .longOpt("path")
            .desc("Path to the file")
            .build();

    final static Option random = new Option("r", "random", false, "Generate random data");

    public final static Options options = new Options()
            .addOption(path)
            .addOption(datasetName)
            .addOption(random)
            ;

    public CommandLine parse(String[] args) throws IllegalArgumentException, ParseException {

        final CommandLineParser commandLineParser = new DefaultParser();
        try {
            return commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("data-reader", options);
    }
}
