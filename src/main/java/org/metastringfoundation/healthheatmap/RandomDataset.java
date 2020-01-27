package org.metastringfoundation.healthheatmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandomDataset implements Dataset {
    private DatasetMetadata metadata;
    private List<String> indicators;
    private List<String> entities;
    private List<List<DataPoint>> dataGroupedByEntities;

    private RandomDataset(builder b){
        generateMetadata(b.name);
        generateIndicators(b.indicatorSize);
        generateEntities(b.entitiesSize);
        generateData(b.indicatorSize, b.entitiesSize);
    }

    private void generateMetadata(String name) {
        metadata = new DatasetMetadata(name);
    }

    private void generateIndicators(int size) {
        indicators = new ArrayList<>();
        for (int indicatorIndex = 0; indicatorIndex < size; indicatorIndex++) {
            String indicatorName = "Indicator " + indicatorIndex;
            indicators.add(indicatorName);
        }
    }

    private void generateEntities(int size) {
        entities = new ArrayList<>();
        for (int entityIndex = 0; entityIndex < size; entityIndex++) {
            String entityName = "Entity " + entityIndex;
            entities.add(entityName);
        }
    }

    private void generateData(int indicatorSize, int entitySize) {
        Random random = new Random();
        dataGroupedByEntities = new ArrayList<>();
        for (int entityIndex = 0; entityIndex < entitySize; entityIndex++) {
            List<DataPoint> dataOfCurrentEntity = new ArrayList<>();
            for (int indicatorIndex = 0; indicatorIndex < indicatorSize; indicatorIndex++) {
                FloatDataPoint thisDataPoint = new FloatDataPoint(random.nextFloat() * 100);
                dataOfCurrentEntity.add(thisDataPoint);
            }
            dataGroupedByEntities.add(dataOfCurrentEntity);
        }
    }

    @Override
    public List<String> getIndicators() {
        return this.indicators;
    }

    @Override
    public List<String> getEntities() {
        return this.entities;
    }

    @Override
    public List<List<DataPoint>> getDataGroupedByEntities() {
        return this.dataGroupedByEntities;
    }

    @Override
    public DatasetMetadata getMetadata() {
        return this.metadata;
    }

    public static class builder {
        private int indicatorSize = 10;
        private int entitiesSize = 10;
        private String name;

        public builder indicators(int size) {
            indicatorSize = size;
            return this;
        }
        public builder entities(int size) {
            entitiesSize = size;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public RandomDataset build() {
            if (this.name == null) {
                Date now = new Date();
                name("Random dataset " + now.getTime());
            }
            return new RandomDataset(this);
        }
    }
}
