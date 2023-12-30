package com.cloudservs.claimtool.handler;

import com.cloudservs.claimtool.datastore.CollectionConstants;
import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.datastore.QueryHandler;
import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.domain.ClientConfig;
import com.cloudservs.claimtool.domain.claim.ClaimForm;
import com.cloudservs.claimtool.utils.SearchParams;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ClientHandler {
    @Autowired
    DBUtils dbUtils;
    @Autowired
    QueryHandler queryHandler;
    String myCollection = CollectionConstants.CLIENT;
    String series = "CLNT";
    Gson gson = new Gson();
    public String save(ClientConfig config){
        if(StringUtils.isEmpty(config.getSeries())) {
            config.setSeries(series);
        }
        return dbUtils.saveObject(myCollection,config);
    }
    public List<Document> fetch(HttpServletRequest request, SearchParams searchParams){
        return dbUtils.findByCriteria(myCollection,queryHandler.getSearchCriteria(myCollection,searchParams),searchParams.getOrderBy(),searchParams.getPageNo(),searchParams.getPageSize());
    }
}
