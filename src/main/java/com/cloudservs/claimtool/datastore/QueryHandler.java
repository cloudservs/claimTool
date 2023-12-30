package com.cloudservs.claimtool.datastore;

import com.cloudservs.claimtool.utils.SearchParams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cloudservs.claimtool.datastore.OperatorConstants.*;

@Component
public class QueryHandler {

      public JSONObject getNewCriteriaJson(String field,Object value,String operator){
       JSONObject newCriteria = new JSONObject();
        switch (operator){
            case "eq":
                 equals(newCriteria,field,value);
                 break;
            case "eqic":
                 equalIgnoreCase(newCriteria,field,value);;
                break;
        }
        return  newCriteria;
    }
    public void equals(JSONObject jsonObject, String field, Object value){
        jsonObject.put (field,value);
    }
    public void equalIgnoreCase(JSONObject jsonObject, String field, Object value){
        JSONObject crieriaJson = new JSONObject();
        crieriaJson.put("$regex","^"+value+"$");
        crieriaJson.put("$options", "i" );
        jsonObject.put (field,crieriaJson);
    }
    public void startsWith(JSONObject jsonObject, String field, Object value){
        JSONObject crieriaJson = new JSONObject();
        crieriaJson.put("$regex","^"+value);
        jsonObject.put (field,crieriaJson);
    }
    public void startsWithIgnoreCase(JSONObject jsonObject, String field, Object value){
        JSONObject crieriaJson = new JSONObject();
        crieriaJson.put("$regex","^"+value);
        crieriaJson.put("$options", "i" );
        jsonObject.put (field,crieriaJson);
    }


    public JSONObject getSearchCriteria(String collection, SearchParams searchParams){
       JSONObject jsonObject = new JSONObject();
        if(searchParams.getCrList()!=null){
            searchParams.getCrList().forEach(searchCriteria -> {
                switch (searchCriteria.getOperator()){
                    case EQUAL:
                        equals(jsonObject,searchCriteria.getfName(),searchCriteria.getfValue());
                        break;
                    case EQUAL_IGNORE_CASE:
                        equalIgnoreCase(jsonObject,searchCriteria.getfName(),searchCriteria.getfValue());
                        break;
                    case STARTS_WITH:
                        startsWith(jsonObject,searchCriteria.getfName(),searchCriteria.getfValue());
                        break;
                    case STARTS_WITH_IGNORE_CASE:
                        startsWithIgnoreCase(jsonObject,searchCriteria.getfName(),searchCriteria.getfValue());
                        break;

                }

            });
        }
    return jsonObject;
    }
}
