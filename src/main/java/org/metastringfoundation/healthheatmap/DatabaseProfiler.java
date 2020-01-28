package org.metastringfoundation.healthheatmap;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class DatabaseProfiler {
    private int datasets = 20;
    private int indicators = 500;
    private int entities = 500;

    Random random = new Random();
    private Timer timer;

    private MongoCollection<Document> collectionOfDatasets;
    private MongoCollection<Document> collectionOfEntities;
    private MongoCollection<Document> skinnyCollection;

    Connection psqlConnection;
    Statement statement;

    String action;


    public DatabaseProfiler(String action) {
        timer = new Timer();
        this.action = action;

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("healthdata");
        collectionOfDatasets = database.getCollection("groupedByDataset");
        collectionOfEntities = database.getCollection("groupedByEntities");
        skinnyCollection = database.getCollection("skinny");
        try {
            psqlConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/healthdata",
                "metastring", "metastring");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(1);
        }
    }

    public void run() {
        if (action.equals("fill")) {
            fillUpMongoDB();
            fillUpPostgresql();
        } else {
            queryPostgresql();
            queryMongoDB();
        }
    }

    private void fillUpMongoDB() {
        timer.reset();
        mongoAsSkinny();
        timer.result("Filling mongo as skinny tables");
    }

    private void fillUpPostgresql() {
        timer.reset();
        try {
            psqlAsDifferentTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        timer.result("Filling up postgresql as different tables");
    }

    private void mongoAsSkinny() {
        for (int entityIndex = 0; entityIndex < entities; entityIndex++) {
            for (int indicatorIndex = 0; indicatorIndex < indicators ; indicatorIndex++) {
                List<WriteModel<Document>> bulkList = new ArrayList<>();
                for (int datasetIndex = 0; datasetIndex < datasets; datasetIndex++) {
                    Document doc = new Document();
                    doc.append("value", random.nextFloat());
                    doc.append("entity", "Entity " + entityIndex);
                    doc.append("indicator", "Indicator " + indicatorIndex);
                    doc.append("dataset", "Dataset " + datasetIndex);
                    bulkList.add(new InsertOneModel<>(doc));
                }
                skinnyCollection.bulkWrite(bulkList);
            }
        }
    }

    private void psqlAsDifferentTables() throws SQLException {
        for (int datasetIndex = 0; datasetIndex < datasets; datasetIndex++) {
            String datasetName = "Dataset" + datasetIndex;
            Statement statement;
            statement = psqlConnection.createStatement();
            String tableCreationSQL = "CREATE TABLE " + datasetName + " " +
                    "(ENTITYNAME TEXT NOT NULL";
            for (int indicatorIndex = 0; indicatorIndex < indicators; indicatorIndex++) {
                String indicatorName = "Indicator" + indicatorIndex;
                tableCreationSQL += ", " + indicatorName + " NUMERIC";
            }
            tableCreationSQL += ");";
            statement.executeUpdate(tableCreationSQL);
            statement.close();

            psqlConnection.setAutoCommit(false);
            statement = psqlConnection.createStatement();
            for (int entityIndex = 0; entityIndex < entities; entityIndex++) {
                String entityName = "Entity" + entityIndex;
                String entityInsertionSQL = "INSERT INTO " + datasetName + " VALUES " +
                        "(" + wrapInQuote(entityName);
                for (int indicatorIndex = 0; indicatorIndex < indicators; indicatorIndex++) {
                    float value = random.nextFloat();
                    entityInsertionSQL += ", " + value;
                }
                entityInsertionSQL += ");";

                statement.executeUpdate(entityInsertionSQL);
            }
            statement.close();
            psqlConnection.commit();
        }
    }

    public static String wrapInQuote(String word) {
        String quote = "'";
        return quote + word + quote;
    }

    private void queryMongoDB() {
        timer.reset();
        askMongoForSomeEntityData();
        timer.result("mongoEntityData");
    }
    private void queryPostgresql() {
        try {
            createPSQLStatement();
            timer.reset();
            askPsqlForSomeEntityData();
            timer.result("psqlEntityData");
            closePSQLStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void askMongoForSomeEntityData() {
        String entityName = "Entity100";
        String datasetName = "Dataset10";
        String indicatorName = "Indicator100";

        skinnyCollection.find(and(
                eq("entity", entityName),
                eq("dataset", datasetName),
                eq("indicator", indicatorName)
        ));
    }

    private void createPSQLStatement() throws SQLException {
        statement = psqlConnection.createStatement();
    }

    private void askPsqlForSomeEntityData() throws SQLException {
        String entityName = "Entity100";
        String datasetName = "Dataset10";
        String indicatorName = "Indicator100";

        String selectSQL;

        selectSQL = "Select " + indicatorName + " from " +datasetName +" WHERE entityname=" + wrapInQuote(entityName) +";";

        statement.executeQuery(selectSQL);
    }

    private void closePSQLStatement() throws SQLException {
        statement.close();
    }
}
