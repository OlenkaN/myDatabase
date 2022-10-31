package com.example.My_Database.storage.mongo;


import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.storage.Storage;
import com.example.My_Database.utils.Result;
import com.example.My_Database.utils.toGson.Deserializer;
import com.example.My_Database.utils.toGson.Serializer;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDBClient implements Storage {
    MongoClient mongoClient;

    public MongoDBClient() {
        MongoClientURI connectionString = new MongoClientURI("mongodb://3.124.2.101:27017/?authSource=olenka13");
        mongoClient = new MongoClient(connectionString);
    }

    @Override
    public ArrayList<String> getDBNames() {
        ArrayList<String> availableDatabases = new ArrayList<>();
        for(String dbName: mongoClient.listDatabaseNames()){
            availableDatabases.add(dbName);
        }
        return availableDatabases;
    }

    @Override
    public Result saveDB(Database db) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(db.getName());
        for(Table table : db.list()){
            mongoDatabase.createCollection(table.getName());
            MongoCollection<Document> collection = mongoDatabase.getCollection(table.getName());
            String jsonString = Serializer.Serialize(table);
            collection.insertOne(Document.parse(jsonString));
        }
        return Result.Success();
    }

    @Override
    public Database loadDB(String dbName) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        Database database = new Database(dbName);

        for(String tableName : mongoDatabase.listCollectionNames()) {
            MongoCollection<Document> collection = mongoDatabase.getCollection(tableName);
            Document tableDocument = collection.find().first();
            assert tableDocument != null;
            Table table = Deserializer.getGson().fromJson(tableDocument.toJson(), Table.class);
            database.addTable(table);
        }

        return database;
    }

    @Override
    public Result dropDB(String dbName) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        mongoDatabase.drop();
        return Result.Success();
    }
}
