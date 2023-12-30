package com.cloudservs.claimtool.domain;

import com.cloudservs.claimtool.domain.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Options extends BaseEntity {
    private String refCode;
    private String clientId ;
    private String optionType;
    private String case_id;
    private String caseName;
    private int index;
    private String fieldName;
    private String shortName;
    private String value;
    private Map<String, String> spec;
    private Map<String,List<CustomField>> specMap;
    private String partyType;
    private List<String> list;
    private Map<String, String> additionalDetails;
    private Map<String, List<String>> multiDropDown;

}

