package org.metastringfoundation.healthheatmap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws IllegalArgumentException {

        try {
            CommandLine commandLine = new CLI().parse(args);

            String path = commandLine.getOptionValue("PATH_OPTION");
            String datasetName = commandLine.getOptionValue("DATASET_NAME_OPTION");

            Dataset dataset = new CSVDataset
                    .builder()
                    .metadata(new DatasetMetadata(datasetName))
                    .path(path)
                    .build();

            DataStorer dataStorer = new MultiplexDataStorer();

            dataStorer.addDataset(dataset);

        } catch (ParseException e) {
            CLI.printHelp();
            System.exit(1);
        } catch (DatasetIntegrityError e) {
            System.out.println("The data has some problem");
            e.printStackTrace();
        }
    }
}
