package com.cloudservs.claimtool.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomField {
    private String type;
    private String label;
    private String field;
    private List<String> values;
}

