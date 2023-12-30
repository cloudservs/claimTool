package com.cloudservs.claimtool.domain.claim;

import java.util.Map;

/**
 * Created by 3719 on 23-Feb-17.
 */
public class IdDetails {
    private String name;
    private String idType;
    private String idNumber;
    private String idExpiryDate;
    private String cinNumber;
    private String nameOnId;
    private String imageUrl;
    private Map<String, String> additionalDetails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String, String> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(Map<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getNameOnId() {
        return nameOnId;
    }

    public void setNameOnId(String nameOnId) {
        this.nameOnId = nameOnId;
    }

    public String getCinNumber() {
        return cinNumber;
    }

    public void setCinNumber(String cinNumber) {
        this.cinNumber = cinNumber;
    }
}
