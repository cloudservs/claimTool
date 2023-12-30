package com.cloudservs.claimtool.handler;

import com.cloudservs.claimtool.datastore.CollectionConstants;
import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.datastore.QueryHandler;
import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.domain.Options;
import com.cloudservs.claimtool.domain.claim.AmountDetails;
import com.cloudservs.claimtool.domain.claim.ClaimDetails;
import com.cloudservs.claimtool.domain.claim.ClaimForm;
import com.cloudservs.claimtool.utils.CommonUtil;
import com.cloudservs.claimtool.utils.SearchParams;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OptionsHandler {
    @Autowired
    DBUtils dbUtils;
    @Autowired
    QueryHandler queryHandler;
    String myCollection = CollectionConstants.OPTIONS;
    String series = "OPT";
    Gson gson = new Gson();
    public String save(Options options){
        if(StringUtils.isEmpty(options.getSeries())) {
            options.setSeries(series);
       }
        return dbUtils.saveObject(myCollection,options);
    }
    public List<Document> fetch(HttpServletRequest request, SearchParams searchParams){
        return dbUtils.findByCriteria(myCollection,queryHandler.getSearchCriteria(myCollection,searchParams),searchParams.getOrderBy(),searchParams.getPageNo(),searchParams.getPageSize());
    }

}
