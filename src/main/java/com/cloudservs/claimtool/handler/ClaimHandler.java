package com.cloudservs.claimtool.handler;

import com.cloudservs.claimtool.datastore.CollectionConstants;
import com.cloudservs.claimtool.datastore.DBUtils;
import com.cloudservs.claimtool.datastore.QueryHandler;
import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.domain.claim.AmountDetails;
import com.cloudservs.claimtool.domain.claim.ClaimDetails;
import com.cloudservs.claimtool.domain.claim.ClaimForm;
import com.cloudservs.claimtool.utils.CommonUtil;
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
public class ClaimHandler {
    @Autowired
    DBUtils dbUtils;
    @Autowired
    QueryHandler queryHandler;
    String myCollection = CollectionConstants.CLAIM;
    String series = "CLM";
    Gson gson = new Gson();
    public String save(ClaimForm claimForm){
        if(StringUtils.isEmpty(claimForm.getSeries())) {
         claimForm.setSeries(series+claimForm.getCaseNo());
       }
        return dbUtils.saveObject(myCollection,claimForm);
    }
    public List<Document> fetch(HttpServletRequest request, SearchParams searchParams){
        return dbUtils.findByCriteria(myCollection,queryHandler.getSearchCriteria(myCollection,searchParams),searchParams.getOrderBy(),searchParams.getPageNo(),searchParams.getPageSize());
    }
    public void calculateTotalAmountDetails( Case cases, ClaimForm claimForm){
        if(claimForm.getClaimAmountDetails() != null && !claimForm.getClaimAmountDetails().isEmpty()){
            try {
                for (ClaimDetails claimDetails : claimForm.getClaimAmountDetails()) {
                    if (claimDetails.getPaymentDetails() != null && !claimDetails.getPaymentDetails().isEmpty()) {
                        for (AmountDetails amountDetails : claimDetails.getPaymentDetails()) {
                            int days = CommonUtil.getNoOfDaysBetweenDates(claimForm.getCommencementDate(), amountDetails.getPaymentDate());
                            if (days > 0) {
                                double interest_amount = amountDetails.getAmount() / 100 * (8 * days / 365);
                                double total_amount = claimForm.getAmount() + interest_amount + amountDetails.getTax() + amountDetails.getPenalty();
                                amountDetails.setInterest_amount(interest_amount);
                                amountDetails.setTotal_amount(total_amount);
                            }
                        }
                    }
                }
            }catch (Exception excep){
                excep.printStackTrace();

            }
        }
    }
}
