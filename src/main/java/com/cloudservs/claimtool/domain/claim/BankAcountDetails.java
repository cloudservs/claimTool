package com.cloudservs.claimtool.domain.claim;

import java.util.Date;

public class BankAcountDetails {
    private String accountNumber;
    private String ifscCode;
    private String micr;
    private String partyName;
    private String branch;
    private String accountStatus;
    private String accountType;
    private String bankName;
    private String bankAltName;
    private String upiId;
    private double closingBal;
    private double openingBal;
    private Date date;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAltName() {
        return bankAltName;
    }

    public void setBankAltName(String bankAltName) {
        this.bankAltName = bankAltName;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(double closingBal) {
        this.closingBal = closingBal;
    }

    public double getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(double openingBal) {
        this.openingBal = openingBal;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }
}
