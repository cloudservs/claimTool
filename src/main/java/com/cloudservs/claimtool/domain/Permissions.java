package com.cloudservs.claimtool.domain;

import com.cloudservs.claimtool.domain.utils.BaseEntity;

import java.util.Map;

public class Permissions extends BaseEntity {
	private String userId;
	private String email;
	private String name;
	private String caseId;
	private String caseSerial;
	private Map<String,Boolean> casePermission;

}
