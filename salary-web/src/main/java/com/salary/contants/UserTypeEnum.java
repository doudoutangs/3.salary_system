package com.salary.contants;

public enum UserTypeEnum {
    NORMAL("1","普通员工"),
    LOGISTICS("2","后勤员工"),
    MANAGE("3","管理员工"),
    ;
    private String code;
    private String name;

    UserTypeEnum(String code, String name) {
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
