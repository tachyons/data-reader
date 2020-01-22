package org.metastringfoundation.healthheatmap;

import java.util.List;

public class CSVDataset implements Dataset {
    private String path;


    CSVDataset(String path) {
        this.path = path;
    }

    public void parseData() {

    }

    @Override
    public List<String> getIndicators() {
        return null;
    }

    @Override
    public List<String> getEntities() {
        return null;
    }

    @Override
    public List<DataPoint> getDataGroupedByIndicators() {
        return null;
    }

    @Override
    public List<DataPoint> getDataGroupedByEntities() {
        return null;
    }
}