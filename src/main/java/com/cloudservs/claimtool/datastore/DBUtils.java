package com.cloudservs.claimtool.datastore;

import com.cloudservs.claimtool.handler.AccountBookHandler;
import com.cloudservs.claimtool.utils.SearchParams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class DBUtils {
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
    public List<Document> findAll(String collection, JSONObject jsonObject){
        try {
            Iterator<Document> itemIerator = database.getCollection(collection).find(Document.parse(jsonObject.toString())).iterator();
            List<Document> documentList =new ArrayList<>();
            if(itemIerator.hasNext()){
                documentList.add(itemIerator.next());
            }
            return documentList;
        }catch (Exception ex){
            return null;
        }
    }
    public Document findOne(String collection, JSONObject jsonObject){
        try {
            Iterator<Document> itemIerator = database.getCollection(collection).find(Document.parse(jsonObject.toString())).iterator();
            if(itemIerator.hasNext()) {
                Document document = itemIerator.next();
                return document;
            }
            return null;
        }catch (Exception ex){
            return null;
        }
    }
    public List<Document> findByCriteria(String collection, JSONObject jsonObject,List<String> orderBy,int pageNo, int pageSize){
        try {
            Iterator<Document> itemIerator = null;
            JSONObject orderByJson=new JSONObject();
            if(orderBy==null || orderBy.isEmpty()){
                orderByJson.put("_id",-1);
            }
            if(pageSize==0){
                pageSize=50;
            }
            itemIerator = database.getCollection(collection).find(Document.parse(jsonObject.toString())).sort(Document.parse(orderByJson.toString())).skip(pageNo*pageSize).limit(pageSize).iterator();
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
