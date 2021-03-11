package com.salary.exception;

import com.salary.contants.ExceptionEnum;

public class GenException extends RuntimeException {

    private Integer code;
    private String message;

    public GenException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public GenException(String message) {
        this.code = 1;
        this.message = message;
    }

    public GenException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
