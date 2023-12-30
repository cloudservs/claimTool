package com.cloudservs.claimtool.domain;

import com.cloudservs.claimtool.domain.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class Case extends BaseEntity {
	private String caseNo;
	private String name;
	private String description;
	private List<String> authorised_person;
	private boolean online;
	private boolean calculateInterestAmount;
	private String caseType;
	private String bench;

	private Date initiationDate;
	private Date submissionDate;
	private Date admissionDate;
	private Date irpAppointementDate;
	private String irpName;
	private String irpAltName;
	Map<String,String> claimTempaltes;
	private Map<String, String> emails;
	private String parentFolder;
}
