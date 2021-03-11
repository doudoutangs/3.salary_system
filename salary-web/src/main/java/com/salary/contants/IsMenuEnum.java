package com.salary.contants;

public enum IsMenuEnum {


    IS_MENU_ENUM("1","是"),
    NO_MENU_ENUM("0","不是"),
    ;

    private String code;
    private String name;

    IsMenuEnum(String code, String name) {
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
