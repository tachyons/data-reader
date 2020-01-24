package org.metastringfoundation.healthheatmap;

public class MultiplexDataStorer implements DataStorer {
    private Database mongodb;

    public MultiplexDataStorer() {
        this.mongodb = new MongoDB();
    }

    @Override
    public void addDataset(Dataset dataset) {
        this.mongodb.addDataset(dataset);
    }
}
