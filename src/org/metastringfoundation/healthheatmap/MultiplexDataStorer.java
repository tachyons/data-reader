package org.metastringfoundation.healthheatmap;

public class MultiplexDataStorer implements DataStorer {
    private Database mongodb;

    public MultiplexDataStorer() {
        this.mongodb = new MongoDB();
    }

    @Override
    public void saveDataset(Dataset dataset) {
        this.mongodb.saveDataset(dataset);
    }
}
