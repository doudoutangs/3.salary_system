package com.salary.contants;

public enum UserPostEnum {
    ONE("1","人事专员"),
    TWO("2","前台接待"),
    THREE("3","部门经理"),
    FOUR("4","技术员"),
    ;
    private String code;
    private String name;

    UserPostEnum(String code, String name) {
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
