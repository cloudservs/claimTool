package com.cloudservs.claimtool.domain.claim;


import com.cloudservs.claimtool.domain.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class ClaimForm extends BaseEntity {
    String concat;
    String category;
    String catClass;
    String claimModel;
    String formName;
    String formNum;
    String formId;
    String claimId;
    String respName;
    String authIP;
    String reviewComment;
    String authorised_person;
    Map <String,String> adFields;
    boolean part_of_coc;
    double interestRate;
    boolean cocEligible;
    boolean notCoc;

    private String comment;
    private String caseId;
    private String caseName;
    private String caseNo;
    long claimSrNumber;
    String claimSeries;
    String claimSerialId;
    List <Claimant> creditors;
    String irpName;
    Address irpAddress;
    Claimant primaryClaimant;
    List<IdDetails> ids;
    List<ClaimDetails> claimAmountDetails;
    String debtDetails;
    String mutualDealing;
    List<SecurityDetails> secDetails;
    BankAcountDetails bankAccount;
    String refForm_id;
    String claimRep;
    String relation;
    String claimRepAddress;
    Date commencementDate;
    boolean relatedParty;
    String signature;
    String place;
    Date claimDate;
    Date formDate;
    String weekday;
    String day;
    String month;
    String claim_consideration;
    String formStatus;
    String formType;
    String user_id;
    String userId;
    String disputeDetails;
    String retentionDetails;
    String sec_int_relinq;
    String assignment_transfer;
    boolean calculateInterestAmount;

    double amount;
    double interest;
    double penalty;
    double total;
    boolean unrelated;
    double approvedAmount;
    double approvedInterest;
    double approvedTotal;
    double rejectedAmount;
    double rejectedInterest;
    double rejectedTotal;
    double inReviewAmount;
    double inReviewInterest;
    double inReviewTotal;
    double approvedPenalty;
    double rejectedPenalty;
    double inReviewPenalty;
    List<String> deficiency;
    List<Reference> deficiency_list;
    boolean incInVoting;
    long count=1;
    long countOfRecords;
    List<AmountDetails> paymentDetails;
    Date joiningDate;
    Date resignationDate;
    Date promotionDate;

    Map<String, List<S3FolderData>> formAttachments;


}
