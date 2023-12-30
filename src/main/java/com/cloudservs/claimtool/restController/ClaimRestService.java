package com.cloudservs.claimtool.restController;

import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.domain.claim.ClaimForm;
import com.cloudservs.claimtool.handler.CaseHandler;
import com.cloudservs.claimtool.handler.ClaimHandler;
import com.cloudservs.claimtool.utils.CommonUtil;
import com.cloudservs.claimtool.utils.SearchCriteria;
import com.cloudservs.claimtool.utils.SearchParams;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/claim")
public class ClaimRestService {
    @Autowired
    ClaimHandler claimHandler;
    CaseHandler caseHandler;
    @PostMapping("/save")
    public String save(@RequestBody ClaimForm claimForm, HttpServletRequest request) {
        return claimHandler.save(claimForm);
    }
    @PostMapping("/fetch")
    public List<Document> retrieve(@RequestBody SearchParams searchParams, HttpServletRequest request) {
        return claimHandler.fetch(request,null);
    }
    @PostMapping("/gt_for_user/{caseId}")
    public List<Document> getUserClaims(@PathVariable String caseId, @RequestBody SearchParams searchParams, HttpServletRequest request) {
        searchParams.setCrList(new ArrayList<>());
        searchParams.getCrList().add(new SearchCriteria("caseId","eq",caseId));
        //searchParams.getCrList().add(new SearchCriteria("user_id","eq",caseId));
        return claimHandler.fetch(request,searchParams);
    }
    @RequestMapping(value = "/nclf/{case_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getBlankClaimForm(@PathVariable String case_id, @RequestBody ClaimForm requestForm) {
        ClaimForm claimForm = new ClaimForm();
        Case myCase = caseHandler.getCaseById(case_id);
        if(myCase!=null) {
            claimForm.setCaseId(case_id);
            claimForm.setCaseName(myCase.getName());
            claimForm.setIrpName(myCase.getIrpName());
            claimForm.setCalculateInterestAmount(myCase.isCalculateInterestAmount());
            claimForm.setInterestRate(8.00);
            claimForm.setFormDate(new Date());
            claimForm.setWeekday(CommonUtil.getWeekDay(claimForm.getFormDate()));
            claimForm.setDay(CommonUtil.getDay(claimForm.getFormDate()));
            claimForm.setMonth(CommonUtil.getMonthName(claimForm.getFormDate()));
            claimForm.setCommencementDate(myCase.getAdmissionDate());
        }
        return claimForm;
    }

}
