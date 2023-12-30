package com.cloudservs.claimtool.handler;

import com.cloudservs.claimtool.authentication.JwtUtil;
import com.cloudservs.claimtool.datastore.CollectionConstants;
import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.datastore.OperatorConstants;
import com.cloudservs.claimtool.datastore.QueryHandler;
import com.cloudservs.claimtool.domain.ToolUser;
import com.cloudservs.claimtool.utils.MessageConstans;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserHandler {
    @Autowired
    DBUtils dbUtils;
    @Autowired
    QueryHandler queryHandler;
    @Autowired
    JwtUtil jwtUtil;
    String myCollection = CollectionConstants.USER;
    String series = "USR";
    Gson gson = new Gson();
    public String registerUser(ToolUser toolUser){
        if(StringUtils.isEmpty(toolUser.getSeries())) {
            toolUser.setSeries(series);
       }
        List<Document> users = fetch(null,queryHandler.getNewCriteriaJson("email",toolUser.getEmail().toLowerCase(), OperatorConstants.EQUAL_IGNORE_CASE));
        if(users==null || users.isEmpty()) {
            if(toolUser.getEmail()!=null && toolUser.getName()!=null && toolUser.getPassword()!=null){
                toolUser.setEmail(toolUser.getEmail().toLowerCase());
                dbUtils.saveObject(myCollection,toolUser);
                return MessageConstans.USER_REGISTRATION_SUCCESSFUL;
            }else {
                return MessageConstans.USER_REGISTRATION_ERROR_NO_DETAILS;
            }
        }else {
            return MessageConstans.USER_ALREADY_EXIST;
        }
    }

    public Map<String,Object>  singInUser(ToolUser toolUser, HttpServletRequest request) {
        List<Document> users = fetch(request, queryHandler.getNewCriteriaJson("email", toolUser.getEmail(), OperatorConstants.EQUAL_IGNORE_CASE));
        Map<String, Object> result = new HashMap<>();
        if (users != null && !users.isEmpty()) {
            Document user = users.get(0);
            if (toolUser.getPassword().equals(user.get("password").toString())) {
                user.remove("password");
                result.put("USER", user);
                result.put("TOKEN", jwtUtil.generateToken(toolUser.getEmail()));
            } else {
                result.put("error", MessageConstans.USER_REGISTRATION_ERROR_NO_DETAILS);
            }
        } else {
            result.put("error", MessageConstans.USER_DOES_NOT_EXIST);
        }
    return result;
    }
    public List<Document> fetch(HttpServletRequest request, JSONObject jsonObject){
        return dbUtils.findAll(myCollection,jsonObject);
    }
}
