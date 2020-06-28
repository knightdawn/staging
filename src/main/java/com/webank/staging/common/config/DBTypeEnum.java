package com.webank.staging.common.config;

public enum DBTypeEnum {

	base("base"), AA0("AA0"), D00("D00");
    private String value;
 
    DBTypeEnum(String value) {
        this.value = value;
    }
 
    public String getValue() {
        return value;
    }
}
