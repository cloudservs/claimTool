package com.cloudservs.claimtool.restController;

import com.cloudservs.claimtool.domain.Case;
import com.cloudservs.claimtool.domain.Options;
import com.cloudservs.claimtool.domain.claim.ClaimForm;
import com.cloudservs.claimtool.handler.CaseHandler;
import com.cloudservs.claimtool.handler.ClaimHandler;
import com.cloudservs.claimtool.handler.OptionsHandler;
import com.cloudservs.claimtool.utils.CTLConstants;
import com.cloudservs.claimtool.utils.CommonUtil;
import com.cloudservs.claimtool.utils.SearchCriteria;
import com.cloudservs.claimtool.utils.SearchParams;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opt")
public class OptionsRestService {
    Logger logger = LoggerFactory.getLogger( OptionsRestService.class );

    @Autowired
    OptionsHandler optionsHandler;
    CaseHandler caseHandler;
    ObjectMapper mapper;

    @RequestMapping(value = "/gtclmstc/{case_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getVariousCaseConfigs(@PathVariable String case_id,HttpServletRequest request) {
        logger.info("getClaimStatic() called {}" + case_id);

        List<Document> documents = optionsHandler.fetch(request,new SearchParams());
        List<Options> options = mapper.convertValue(documents, new TypeReference<List<Options>>() {});
        List<Options> optionsList = options.stream().filter(option->option.getOptionType().equals(CTLConstants.OPTIONS_CASE_CLAIM_CATEGORY)).collect(Collectors.toList());
        Map<String,String> calimCategory=new HashMap<>();
        Map<String,Map<String,Set<String>>> calimTypeMap=new HashMap<>();
        optionsList.forEach(option->{
            calimCategory.put(option.getShortName(),option.getFieldName());
            Map<String,Set<String>> facilityMap = new HashMap<String, Set<String>>();
            option.getSpec().forEach((calimType,facility)->{
                facilityMap.put(calimType, new HashSet<String>(Arrays.asList(facility.split(";"))));
            });
            calimTypeMap.put(option.getShortName(),facilityMap);
        });
        Options claimantTypes = options.stream().filter(option->option.getOptionType().equals(CTLConstants.OPTIONS_CASE_CLAIMANT_TYPE)).collect(Collectors.toList()).get(0);
        Options claimstatus = options.stream().filter(option->option.getOptionType().equals(CTLConstants.OPTIONS_CASE_CLAIM_STATUS)).collect(Collectors.toList()).get(0);
        Options claim_identifier = options.stream().filter(option->option.getOptionType().equals("claim_identifier")).collect(Collectors.toList()).get(0);
        Options projStatus = options.stream().filter(option->option.getOptionType().equals("project_status_list")).collect(Collectors.toList()).get(0);
        Options securityList = options.stream().filter(option->option.getOptionType().equals("securityList")).collect(Collectors.toList()).get(0);
        Options claimSubTypes = options.stream().filter(option->option.getOptionType().equals("claimSubTypes")).collect(Collectors.toList()).get(0);
        Options claimantClass = options.stream().filter(option->option.getOptionType().equals("claimant_class")).collect(Collectors.toList()).get(0);
        Options formList = options.stream().filter(option->option.getOptionType().equals(CTLConstants.OPTIONS_CASE_FORM_LIST)).collect(Collectors.toList()).get(0);

        Map<String, Object> result = new HashMap<>();
        try {
            if(calimCategory!=null) result.put("claimCategory", calimCategory);
            if(calimTypeMap!=null) result.put("calimType", calimTypeMap);
            if(claimantTypes!=null){
                result.put("partyType", claimantTypes.getList());
                result.put("catPartyMap", claimantTypes.getMultiDropDown());
            }
            if(claimSubTypes!=null){
                result.put("claimSubTypes", claimSubTypes.getList());
                result.put("claimSubTypesCatMap", claimSubTypes.getMultiDropDown());
            }
            if(claimantClass!=null){
                result.put("claimantClass", claimantClass.getMultiDropDown());
            }
            if(claimstatus!=null) result.put("claimstatus", claimstatus.getList());
            if(claim_identifier!=null) result.put("claim_identifier", claim_identifier);
            if(securityList!=null){
                result.put("securityList", securityList.getList());
                result.put("securityListMap", securityList.getSpecMap());
            }
            if (projStatus != null) {
                result.put("projStatus", projStatus.getList());
            }
            if(formList!=null && formList.getSpec()!=null){
                result.put("formList", formList.getSpec());
            }

        }catch (Exception e){
            logger.info("Error while fetching Claim Static");
        }
        return result;
    }

}
