package com.cloudservs.claimtool.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SearchParams {
   private List<SearchCriteria> crList;
   private String key1;
   private String key2;
   private int pageNo;
   private int pageSize;
   private List<String> orderBy;

}
