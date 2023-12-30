package com.cloudservs.claimtool.domain.utils;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class BaseEntity {
    String _id = new ObjectId().toString();
    String refCode;
    String series;
    long srNumber;
    LogUser createdBy;
    LogUser updatedBy;
}
