package org.metastringfoundation.healthheatmap;

public class Main {

    public static void main(String[] args) throws DatasetIntegrityError, IllegalArgumentException {
        String path = new CLI().parse(args);
        Dataset dataset = new CSVDataset
                .builder()
                .path(path)
                .build();

        DataStorer dataStorer = new MultiplexDataStorer();

        dataStorer.saveDataset(dataset);

    }
}
