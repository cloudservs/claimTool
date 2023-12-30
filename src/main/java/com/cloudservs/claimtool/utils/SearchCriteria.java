package com.cloudservs.claimtool.utils;

public class SearchCriteria {
		String operator;
		String fieldType;
		String fName;
	    String fValue;
	public SearchCriteria() {
	}
	public SearchCriteria(String fName, String operator, String fValue) {
		this.fName=fName;
		this.operator=operator;
		this.fValue=fValue;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfValue() {
		return fValue;
	}

	public void setfValue(String fValue) {
		this.fValue = fValue;
	}
}
