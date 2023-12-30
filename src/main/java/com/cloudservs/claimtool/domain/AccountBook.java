package com.cloudservs.claimtool.domain;

import com.cloudservs.claimtool.domain.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Created by 3719 on 03-Dec-17.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountBook extends BaseEntity {
    // private String refCode;
    private String case_id;
    private String caseName;
    private String bookType;
    private String bookName;
    private String fYear;
    private String fDay;
    private String month;
    private long yearCount;
    private long monthCount;
    private long dayCount;


}
