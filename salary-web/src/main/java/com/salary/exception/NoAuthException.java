package com.salary.exception;

import com.salary.contants.ExceptionEnum;

/**
 * 权限注解异常
 *
 * @author sun
 */
public class NoAuthException extends RuntimeException {
    private Integer code;
    private String message;

    public NoAuthException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public NoAuthException(String message) {
        this.code = 1;
        this.message = message;
    }

    public NoAuthException(ExceptionEnum exceptionEnum) {
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
