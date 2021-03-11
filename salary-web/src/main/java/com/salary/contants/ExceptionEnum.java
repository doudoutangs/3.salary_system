package com.salary.contants;

public enum ExceptionEnum {
    SYSTEM_ERRO(500,"系统错误"),
    NO_AUTH(500,"您没有操作权限"),
    TOKEN_EXPIRED(500, "token过期"),
    TOKEN_ERROR(500, "token验证失败"),
    GEN_ERROR(500, "代码生成失败"),
    ;

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
