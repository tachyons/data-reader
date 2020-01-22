package org.metastringfoundation.healthheatmap;

public class Main {

    public static void main(String[] args) throws DatasetIntegrityError, IllegalArgumentException {
        String path = new CLI().parse(args);
        Dataset dataset = new CSVDataset(path);

        DataStorer dataStorer = new MultiplexDataStorer();

        dataStorer.saveDataset(dataset);

    }
}
