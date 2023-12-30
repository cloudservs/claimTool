package com.cloudservs.claimtool.domain.claim;


import com.cloudservs.claimtool.domain.utils.BaseEntity;

import java.util.List;
import java.util.Map;

public class S3FolderData extends BaseEntity {
    private String refCode;
    private String type;
    private String bucket;
    private String rollName;
    private String fileName;
    private String modifiedName;
    private String fileType;
    private long fileSize;
    private boolean isFolder;
    private String parentId;
    private String parentFolder;
    private String eTag;
    private String parentTag;
    private String key;
    private String capKey;
    private String fileExt;
    private String contentType;
    private String caseId;
    private String caseRoot;
    private String caseDescription;

    private int pageCount;
    private boolean readyFrView;
    private String downLoadLink;
    Map<String, List<String>> reference;
    List<String> labels;

    boolean notify;

    private List<S3FolderData> children;
    private int pageNo;
    private List<UploadData> uploadData;
    private boolean collapsed = true;

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getRollName() {
        return rollName;
    }

    public void setRollName(String rollName) {
        this.rollName = rollName;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getParentTag() {
        return parentTag;
    }

    public void setParentTag(String parentTag) {
        this.parentTag = parentTag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public List<UploadData> getUploadData() {
        return uploadData;
    }

    public void setUploadData(List<UploadData> uploadData) {
        this.uploadData = uploadData;
    }

    public String getCapKey() {
        return capKey;
    }

    public void setCapKey(String capKey) {
        this.capKey = capKey;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getCaseRoot() {
        return caseRoot;
    }

    public void setCaseRoot(String caseRoot) {
        this.caseRoot = caseRoot;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isReadyFrView() {
        return readyFrView;
    }

    public void setReadyFrView(boolean readyFrView) {
        this.readyFrView = readyFrView;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Map<String, List<String>> getReference() {
        return reference;
    }

    public void setReference(Map<String, List<String>> reference) {
        this.reference = reference;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getDownLoadLink() {
        return downLoadLink;
    }

    public void setDownLoadLink(String downLoadLink) {
        this.downLoadLink = downLoadLink;
    }

    public List<S3FolderData> getChildren() {
        return children;
    }

    public void setChildren(List<S3FolderData> children) {
        this.children = children;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
    public String getS3Key(String folderKey){
        if(folderKey!=null) {
            String bucketName = folderKey.split( "/" )[0];
            String key = folderKey.replace( bucketName + "/", "" );
            if(Character.compare(key.charAt( key.length()-1 ),'/')==0){
                key = key.substring( 0,key.length()-1 );
            }
            return key;
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

