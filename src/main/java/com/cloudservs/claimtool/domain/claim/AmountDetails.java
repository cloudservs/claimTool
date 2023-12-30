package com.cloudservs.claimtool.domain.claim;

import java.util.Date;

public class AmountDetails {
    int index;
    String unit;
    double total;
    double amount;
    double interest;
    double penalty;
    double tax;
    String comment;
    double approvedAmount;
    double approvedInterest;
    double approvedPenalty;
    double approvedTotal;
    double rejectedAmount;
    double rejectedInterest;
    double rejectedPenalty;
    double rejectedTotal;
    double inReviewAmount;
    double inReviewInterest;
    double inReviewTotal;
    double inReviewPenalty;

    double total_amount;  // calculate upon fixed 8 % interest
    double interest_amount;  //calculate upon fixed 8 % interest

    Date paymentDate;
    Date dueDate;
    Date date;
    String mode;
    String reference;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public double getRejectedAmount() {
        return rejectedAmount;
    }

    public void setRejectedAmount(double rejectedAmount) {
        this.rejectedAmount = rejectedAmount;
    }

    public double getApprovedInterest() {
        return approvedInterest;
    }

    public void setApprovedInterest(double approvedInterest) {
        this.approvedInterest = approvedInterest;
    }

    public double getRejectedInterest() {
        return rejectedInterest;
    }

    public void setRejectedInterest(double rejectedInterest) {
        this.rejectedInterest = rejectedInterest;
    }

    public double getApprovedTotal() {
        return approvedTotal;
    }

    public void setApprovedTotal(double approvedTotal) {
        this.approvedTotal = approvedTotal;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public double getApprovedPenalty() {
        return approvedPenalty;
    }

    public void setApprovedPenalty(double approvedPenalty) {
        this.approvedPenalty = approvedPenalty;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getInterest_amount() {
        return interest_amount;
    }

    public void setInterest_amount(double interest_amount) {
        this.interest_amount = interest_amount;
    }
}
