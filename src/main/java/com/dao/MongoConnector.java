package com.dao;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;

public class MongoConnector {
    MongoClientURI uri  = new MongoClientURI("");
    MongoClient client = new MongoClient(uri);

    public MongoClient connect() throws UnknownHostException {
        try{

            MongoDatabase db = client.getDatabase(uri.getDatabase());
            //client.close();

        }catch (MongoException e) {
            e.printStackTrace();
        }
        return client;
    }

    public MongoDatabase getDatabase(){
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        return db;
    }

    public MongoCollection<Document> getCollection(MongoDatabase db){
        MongoCollection<Document> collection = db.getCollection("desitv");
        return collection;
    }

    public void closeClient(){
        client.close();
    }

}
