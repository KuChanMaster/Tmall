package com.example.common.enums;

public enum StatusEnum {
    UNDER_REVIEW("审核中"),
    PASS_THE_AUDIT("审核通过"),

    FAIL_THE_AUDIT("审核未通过")
    ;

    public String status;
    StatusEnum(String status) {
        this.status = status;
    }
}
