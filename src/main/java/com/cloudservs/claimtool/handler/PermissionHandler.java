package com.cloudservs.claimtool.handler;

import com.cloudservs.claimtool.datastore.CollectionConstants;
import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.domain.ClientConfig;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PermissionHandler {
    @Autowired
    DBUtils dbUtils;
    String myCollection = CollectionConstants.PERMISSIONS;
    Gson gson = new Gson();
    String series = "PERMS";
   /* public String save(Per config){
        if(StringUtils.isEmpty(config.getSeries())) {
            config.setSeries(series);
        }
        return dbUtils.saveObject(myCollection,config);
    }
    public List<Document> fetch(HttpRequest request, Map<String,Object> queryCriteria){
        return dbUtils.findAll(myCollection,queryCriteria);
    }*/
}
