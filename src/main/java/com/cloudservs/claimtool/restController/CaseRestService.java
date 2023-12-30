package com.cloudservs.claimtool.restController;

import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.handler.CaseHandler;
import com.cloudservs.claimtool.utils.SearchCriteria;
import com.cloudservs.claimtool.utils.SearchParams;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/case")
public class CaseRestService {
    CaseHandler caseHandler;
    @PostMapping("/save")
    public String save(@RequestBody Case clientcase, HttpServletRequest request) {
       return caseHandler.save(clientcase);
    }
    @PostMapping("/fetch")
    public List<Document> retrieve(@RequestBody SearchParams searchParams, HttpServletRequest request) {
        return caseHandler.fetch(request,searchParams);
    }
    @PostMapping("/getlivecases")
    public List<Document> getLiveCasesForClaimSubmission(@RequestBody SearchParams searchParams, HttpServletRequest request) {
        searchParams = new SearchParams();
        searchParams.setCrList(new ArrayList<>());
        searchParams.getCrList().add(new SearchCriteria("online", "eq", "true"));
        return caseHandler.fetch(request,searchParams);
    }

}
