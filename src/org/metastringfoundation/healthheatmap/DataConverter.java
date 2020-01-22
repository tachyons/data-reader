package org.metastringfoundation.healthheatmap;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static List<Document> getAsMongoDocuments(Dataset dataset) {
        List<Document> documents = new ArrayList<>();

        List<String> indicators = dataset.getIndicators();
        List<String> entities = dataset.getEntities();
        List<List<DataPoint>> dataGroupedByEntities = dataset.getDataGroupedByEntities();

        for (int entityIndex = 0; entityIndex < entities.size() ; entityIndex++) {
            String currentEntity = entities.get(entityIndex);
            Document doc = new Document("entity", currentEntity);
            for (int indicatorIndex = 0; indicatorIndex < indicators.size(); indicatorIndex++) {
                doc.append(indicators.get(indicatorIndex), dataGroupedByEntities.get(entityIndex).get(indicatorIndex).getValue());
            }

            documents.add(doc);
        }
        return documents;
    }
}
