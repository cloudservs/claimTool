package com.cloudservs.claimtool.handler;

import com.cloudservs.claimtool.datastore.CollectionConstants;
import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.datastore.OperatorConstants;
import com.cloudservs.claimtool.datastore.QueryHandler;
import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.utils.SearchParams;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CaseHandler {
    @Autowired
    DBUtils dbUtils;
    @Autowired
    QueryHandler queryHandler;
    String myCollection = CollectionConstants.CASE;
    String series = "CS";
    Gson gson = new Gson();
    public String save(Case clientCase){
        if(clientCase.getSrNumber()==0) {
            clientCase.setSeries(series);
        }
        return dbUtils.saveObject(myCollection,clientCase);
    }
    public List<Document> fetch(HttpServletRequest request, SearchParams searchParams){
         return dbUtils.findByCriteria(myCollection,queryHandler.getSearchCriteria(myCollection,searchParams),searchParams.getOrderBy(),searchParams.getPageNo(),searchParams.getPageSize());
    }
    public Case getCaseById(String _id){
        Document document = dbUtils.findOne(myCollection,queryHandler.getNewCriteriaJson("_id",_id, OperatorConstants.EQUAL));
        if(document!=null){
            return  gson.fromJson(gson.toJson(document),Case.class);
        }
        return null;
    }
}
