package com.cloudservs.claimtool.domain;

import com.cloudservs.claimtool.domain.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ClientConfig extends BaseEntity {
	String name;
	String details;
	String address;
	String phoneNo;
	String mobile1;
	String mobile2;
	List<String> emails;
	Date registrationDate;
	Date expiryDate;
	boolean active;
}
