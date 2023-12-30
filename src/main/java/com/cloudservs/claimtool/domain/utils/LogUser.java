package com.cloudservs.claimtool.domain.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogUser {
    String _id;
    String name;
    String email;
    Date date;
}
