package com.cloudservs.claimtool.domain;

import com.cloudservs.claimtool.domain.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class ToolUser extends BaseEntity {
	private String name;
	private String password;
	private String email;
	private String mobile;
	private boolean active;
	private boolean clientAdmin;
	private boolean superAdmin;
	private boolean emailVerified;
	private boolean mobileVerified;
	private String emailToken;
	private int mobileToken;
	private String resetPasswordId;
	private String resetTokenValidTill;
	private List<String> modules;
}
