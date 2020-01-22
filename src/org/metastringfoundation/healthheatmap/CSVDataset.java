package org.metastringfoundation.healthheatmap;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CSVDataset implements Dataset {
    private String path;
    private CSVReader csvReader;
    private List<CSVRecord> records;

    private List<String> indicators;
    private List<String> entities;
    private List<List<DataPoint>> dataGroupedByEntities;

    CSVDataset(String path) throws DatasetIntegrityError {
        this.path = path;
        parseData();
    }

    public void parseData() throws DatasetIntegrityError {
        this.csvReader = new CSVReader(path);
        this.records = csvReader.getCSVRecords();
        calculateIndicators();
        System.out.println("Found these indicators " + this.indicators.toString());
        calculateEntities();
        System.out.println("Found these entities " + this.entities.toString());
        calculateDataGroupedByEntities();
    }

    private void calculateIndicators() throws DatasetIntegrityError {
        List<String> indicators = new ArrayList<>();
        CSVRecord firstRow = this.records.get(0);
        Iterator<String> rowIterator = firstRow.iterator();
        rowIterator.next(); // because the top left cell is the name of the entities column
        while (rowIterator.hasNext()) {
            indicators.add(rowIterator.next());
        }

        if (indicators.size() == 0 ) {
            throw new DatasetIntegrityError("No Indicators found in file");
        }

        this.indicators = indicators;
    }

    @Override
    public List<String> getIndicators() {
        return this.indicators;
    }


    private void calculateEntities() throws DatasetIntegrityError {
        List<String> entities = new ArrayList<>();

        // we start from row 1 assuming that row 0 is headers
        for (CSVRecord row: this.records.subList(1, this.records.size())) {
            String entityName = row.get(0);
            entities.add(entityName);
        }

        if (entities.size() == 0) {
            throw new DatasetIntegrityError("No entities found in file");
        }

        this.entities = entities;
    }

    @Override
    public List<String> getEntities() {
        return this.entities;
    }

     private void calculateDataGroupedByEntities() throws DatasetIntegrityError {
        List<List<DataPoint>> dataGroupedByEntities = new ArrayList<>();

        for (CSVRecord row: this.records.subList(1, this.records.size())) {

            List<DataPoint> dataOfThisEntity = new ArrayList<>();

            // skipping the first column because that is where the entity names are
            for (int indicatorIndex = 1; indicatorIndex < row.size(); indicatorIndex++) {
                try {
                    dataOfThisEntity.add(new StringDataPoint(row.get(indicatorIndex)));
                } catch (IndexOutOfBoundsException e) {
                    dataOfThisEntity.add(new StringDataPoint("NA"));
                }
            }

            dataGroupedByEntities.add(dataOfThisEntity);

        }

        if (dataGroupedByEntities.size() == 0) {
            throw new DatasetIntegrityError("No data rows found at all");
        }

         this.dataGroupedByEntities = dataGroupedByEntities;
     }

    @Override
    public List<List<DataPoint>> getDataGroupedByEntities() {
        return this.dataGroupedByEntities;
    }
}