package com.cloudservs.claimtool.datastore;

import com.cloudservs.claimtool.handler.AccountBookHandler;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DBHandler {
    @Autowired
    private MongoClient mongoClient;
    public MongoDatabase database = null;
    @Value("${spring.data.mongodb.database}")
    String databaseName;
    @Autowired
    AccountBookHandler accountBookHandler;
    Gson gson = new Gson();
    @PostConstruct
    public void prepareDatabase(){
        database = mongoClient.getDatabase(databaseName);
     }

    public Document getDocument(Object clientCase){
        return Document.parse(gson.toJson(clientCase));
    }
    public String saveObject(String collection, Object object){
        try {
            Document document =getDocument(object);
            if(document.getInteger("srNumber")==0) {
                long srNumber = accountBookHandler.getBookSerialNumber(document.getString("refCode"),document.getString("series"));
                document.put("srNumber",srNumber);
            }else{
                document.put("version",document.getInteger("version")+1);
            }
            database.getCollection(collection).insertOne(document);
            return "ok";
        }catch (Exception ex){
            return "Error:"+ex.getMessage();
        }
    }
    public List<Document> findAll(String collection, Object object){
        try {
            Iterator<Document> itemIerator = database.getCollection(collection).find().iterator();
            List<Document> documentList =new ArrayList<>();
            if(itemIerator.hasNext()){
                documentList.add(itemIerator.next());
            }
            return documentList;
        }catch (Exception ex){
            return null;
        }
    }
}
