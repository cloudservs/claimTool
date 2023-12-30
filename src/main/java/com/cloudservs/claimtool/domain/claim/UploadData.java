package com.cloudservs.claimtool.domain.claim;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class UploadData {
	private byte[] fileData;
	/*
	 * This variable is the folder path under bucket where the file will be saved
	 */
	private String innerBucketPath;
	private String fileName;
	private String fileExtn;
	private List<String> toEmailList;
	private List<String> ccEmailList;
	private List<String> bccEmailList;
	private Map<String, String> additionalDetails;	
	private boolean publicFile;
	private String publicUrl;

}
