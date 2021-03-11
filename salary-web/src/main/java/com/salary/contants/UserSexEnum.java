package com.salary.contants;

public enum UserSexEnum {
    MAN("1","男"),
    WOMAN("2","女"),
    ;
    private String code;
    private String name;

    UserSexEnum(String code, String name) {
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
