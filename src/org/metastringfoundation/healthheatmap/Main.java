package org.metastringfoundation.healthheatmap;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.csv.*;
import org.bson.Document;

import javax.xml.crypto.Data;

public class Main {

    public static Document convertRecordtoDocument(CSVRecord record, List<String> headers) {
        Document doc = new Document();
        for (String header: headers) {
            doc.append(header, record.get(header));
        }
        return doc;
    }

    public static List<Document> convertCSVtoMongoDocumentList(Iterable<CSVRecord> records) {
        List<Document> documents = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        headers.add("Some");
        for (CSVRecord record: records) {
            documents.add(convertRecordtoDocument(record, headers));
        }
        return documents;
    }

    public static void insertDataIntoMongo(List<Document> documents) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("healthdata");
        MongoCollection<Document> collection = database.getCollection("data");
        collection.insertMany(documents);
    }


    public static void main(String[] args) throws DatasetIntegrityError, IllegalArgumentException {
        String path = new CLI().parse(args);
        CSVReader csvReader = new CSVReader(path);
        csvReader.readCSV();
    }
}
