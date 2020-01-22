package org.metastringfoundation.healthheatmap;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class MongoDB implements Database {
    private MongoCollection<Document> collection;

    public MongoDB() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("healthdata");
        this.collection = database.getCollection("data");
    }

    @Override
    public void saveDataset(Dataset dataset) {
        List<Document> documents = new DataConverter().getAsMongoDocuments(dataset);
        this.collection.insertMany(documents);
    }
}
