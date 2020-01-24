package org.metastringfoundation.healthheatmap;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class MongoDB implements Database {
    private MongoCollection<Document> collectionOfDatasets;
    private MongoCollection<Document> collectionOfEntities;

    public MongoDB() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("healthdata");
        this.collectionOfDatasets = database.getCollection("groupedByDataset");
        this.collectionOfEntities = database.getCollection("groupedByEntities");
    }

    @Override
    public void addDataset(Dataset dataset) {
        addDatasetAsDatasets(dataset);
        addDatasetAsEntities(dataset);

    }

    private void addDatasetAsDatasets(Dataset dataset) {
        List<String> indicators = dataset.getIndicators();
        List<String> entities = dataset.getEntities();
        List<List<DataPoint>> dataGroupedByEntities = dataset.getDataGroupedByEntities();
        DatasetMetadata metadata = dataset.getMetadata();

        String datasetName = metadata.getName();


        Bson datasetFilter = eq("_metadata.name", datasetName);
        List<Bson> datasetUpdates = new ArrayList<>();

        for (int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
            String entityName = entities.get(entityIndex);

            for (int indicatorIndex = 0; indicatorIndex < indicators.size(); indicatorIndex++) {
                String indicatorName = indicators.get(indicatorIndex);
                String fieldToUpdate = entityName + "." + indicatorName;
                Object value = dataGroupedByEntities.get(entityIndex).get(indicatorIndex).getValue();
                datasetUpdates.add(set(fieldToUpdate, value));
            }
        }

        Bson datasetUpdate = combine(datasetUpdates);

        UpdateOptions upsert = new UpdateOptions().upsert(true);
        this.collectionOfDatasets.updateOne(
                datasetFilter,
                datasetUpdate,
                upsert
        );
    }

    private void addDatasetAsEntities(Dataset dataset) {
        List<String> indicators = dataset.getIndicators();
        List<String> entities = dataset.getEntities();
        List<List<DataPoint>> dataGroupedByEntities = dataset.getDataGroupedByEntities();
        DatasetMetadata metadata = dataset.getMetadata();

        String datasetName = metadata.getName();

        for (int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
            String entityName = entities.get(entityIndex);
            Bson datasetFilter = eq("name", entityName);

            List<Bson> datasetUpdates = new ArrayList<>();

            for (int indicatorIndex = 0; indicatorIndex < indicators.size(); indicatorIndex++) {
                String indicatorName = indicators.get(indicatorIndex);
                String fieldToUpdate = indicatorName;
                Object value = dataGroupedByEntities.get(entityIndex).get(indicatorIndex).getValue();
                Document dataDoc = new Document();
                dataDoc.append("value", value);
                dataDoc.append("source", datasetName);
                datasetUpdates.add(addToSet(fieldToUpdate, dataDoc));
            }
            Bson datasetUpdate = combine(datasetUpdates);

            UpdateOptions upsert = new UpdateOptions().upsert(true);
            this.collectionOfEntities.updateOne(
                    datasetFilter,
                    datasetUpdate,
                    upsert
            );
        }
    }
}