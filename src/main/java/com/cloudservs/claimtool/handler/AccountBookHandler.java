package com.cloudservs.claimtool.handler;


import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.datastore.QueryHandler;
import com.cloudservs.claimtool.domain.AccountBook;
import com.cloudservs.claimtool.domain.utils.LogUser;
import com.cloudservs.claimtool.utils.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AccountBookHandler {
    static Logger logger = LoggerFactory.getLogger(AccountBookHandler.class);
	String collectionName = "ct_accountBook";
	@Autowired
	private MongoClient mongoClient;
	@Value("${spring.data.mongodb.database}")
	String databaseName;
	@Autowired
	QueryHandler queryHandler;
	Gson gson = new Gson();
	private MongoDatabase getDatabase(){
		return mongoClient.getDatabase(databaseName);
	}
	public AccountBook getAccountBook(String refCode, String series){
		AccountBook accountBook =null;
		try {
			boolean isNewBook = false;
			logger.info("Calling to fetch Account Book for {} : {}",refCode,series);
			if(refCode!=null){
				JSONObject newCriteria = new JSONObject();
				queryHandler.equals(newCriteria,"refCode",refCode);
				queryHandler.equals(newCriteria,"series",series);
				accountBook = (AccountBook) getDatabase().getCollection(collectionName).find(Document.parse(newCriteria.toString())).limit(1);
			}
			AccountBook newBook = new AccountBook();
			if (accountBook == null) {
				logger.info("Account Book not found for {} : {}",refCode,series);
				newBook = getNewAccountBook(refCode, series, null);
				isNewBook = true;
			}
			if (isNewBook) {
				logger.info("Returning new Book found for {}: {} : {}",newBook.getSeries(),newBook.get_id(),newBook.getSrNumber());
				return newBook;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		logger.info("Returning saved Book found for {} : {} : {}",accountBook.getSeries(),accountBook.get_id(),accountBook.getSrNumber());
		return accountBook;
	}
	private AccountBook getNewAccountBook(String refCode,String series ,Date date ) {
		AccountBook accountBook = new AccountBook();
		accountBook.set_id(new ObjectId().toString());
		accountBook.setRefCode(refCode);
		accountBook.setSeries(series);
		if(date==null) date = new Date(  );
		accountBook.setFYear(CommonUtil.getFinancialYear(date));
		accountBook.setMonth(CommonUtil.getMonth(date));
		accountBook.setFDay(CommonUtil.getFinancialDay(date));
		accountBook.setSrNumber(0);
		accountBook.setCreatedBy(new LogUser());
		accountBook.getCreatedBy().setDate(new Date());
		return accountBook;
	}
    public long getBookSerialNumber (String refCode, String series){
        long transactionSerialNumber =1;
        AccountBook accountBook = getAccountBook(refCode, series);
        if(accountBook!=null){
            transactionSerialNumber = (accountBook.getSrNumber()+1);
            accountBook.setSrNumber( transactionSerialNumber);
			accountBook.setUpdatedBy(new LogUser());
			accountBook.getUpdatedBy().setDate(new Date());
            logger.info("Saving account Book with new SerialNumber {} : {} : {}",accountBook.getCase_id(),accountBook.getSeries(),accountBook.getSrNumber());
            //queryHandler.saveObject(AccountBook.class, accountBook);
			getDatabase().getCollection(collectionName).insertOne(Document.parse(gson.toJson(accountBook)));
        }
        return transactionSerialNumber;
    }


}
