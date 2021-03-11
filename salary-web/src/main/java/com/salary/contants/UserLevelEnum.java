package com.salary.contants;

public enum UserLevelEnum {
    ONE("1","一级"),
    TWO("2","二级"),
    THREE("3","三级"),
    FOUR("4","四级"),
    FIVE("5","五级"),
    ;
    private String code;
    private String name;

    UserLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
