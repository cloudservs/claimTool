package com.cloudservs.claimtool.domain.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ClaimDetails {
    UnitDetails unitDetails;
    int index;
    String unit; // UNit no, CLaim Type, Invoice Id etc
    String type; // unitType, Facility Type, Type
    String name; // Name
    double area;
    Date date;
    String mode;
    String reference;
    Map <String,String> adFields;
    double total;

    double amount;
    double interest;
    double penalty;

    double approvedAmount;
    double approvedPenalty;
    double rejectedPenalty;
    double inReviewPenalty;
    double approvedInterest;
    double approvedTotal;
    double rejectedAmount;
    double rejectedInterest;
    double rejectedTotal;
    double inReviewAmount;
    double inReviewInterest;
    double inReviewTotal;
    List<AmountDetails> paymentDetails;

    public UnitDetails getUnitDetails() {
        return unitDetails;
    }

    public void setUnitDetails(UnitDetails unitDetails) {
        this.unitDetails = unitDetails;
    }

    public List <AmountDetails> getPaymentDetails() {
        if(paymentDetails==null) paymentDetails=new ArrayList <>();
        return paymentDetails;
    }

    public void setPaymentDetails(List <AmountDetails> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double getApprovedPenalty() {
        return approvedPenalty;
    }

    public void setApprovedPenalty(double approvedPenalty) {
        this.approvedPenalty = approvedPenalty;
    }

    public double getRejectedPenalty() {
        return rejectedPenalty;
    }

    public void setRejectedPenalty(double rejectedPenalty) {
        this.rejectedPenalty = rejectedPenalty;
    }

    public double getInReviewPenalty() {
        return inReviewPenalty;
    }

    public void setInReviewPenalty(double inReviewPenalty) {
        this.inReviewPenalty = inReviewPenalty;
    }

    public double getApprovedInterest() {
        return approvedInterest;
    }

    public void setApprovedInterest(double approvedInterest) {
        this.approvedInterest = approvedInterest;
    }

    public double getApprovedTotal() {
        return approvedTotal;
    }

    public void setApprovedTotal(double approvedTotal) {
        this.approvedTotal = approvedTotal;
    }

    public double getRejectedAmount() {
        return rejectedAmount;
    }

    public void setRejectedAmount(double rejectedAmount) {
        this.rejectedAmount = rejectedAmount;
    }

    public double getRejectedInterest() {
        return rejectedInterest;
    }

    public void setRejectedInterest(double rejectedInterest) {
        this.rejectedInterest = rejectedInterest;
    }

    public double getRejectedTotal() {
        return rejectedTotal;
    }

    public void setRejectedTotal(double rejectedTotal) {
        this.rejectedTotal = rejectedTotal;
    }

    public double getInReviewAmount() {
        return inReviewAmount;
    }

    public void setInReviewAmount(double inReviewAmount) {
        this.inReviewAmount = inReviewAmount;
    }

    public double getInReviewInterest() {
        return inReviewInterest;
    }

    public void setInReviewInterest(double inReviewInterest) {
        this.inReviewInterest = inReviewInterest;
    }

    public double getInReviewTotal() {
        return inReviewTotal;
    }

    public void setInReviewTotal(double inReviewTotal) {
        this.inReviewTotal = inReviewTotal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Map <String, String> getAdFields() {
        return adFields;
    }

    public void setAdFields(Map <String, String> adFields) {
        this.adFields = adFields;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
}
