package com.salary.core;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 异步返回实体类
 *
 * @author sun
 */
public class R {


    private boolean success;
    private int code;
    private String message;
    private Object obj;
    private List<?> data;

    public static R success(String message) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage(message);
        return r;
    }

    public static R success(Object obj) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setObj(obj);
        return r;
    }

    public static R success(List<?> data) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setData(data);
        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(500);
        r.setMessage(message);
        return r;
    }

    public static Map<String, Object> return4Page(IPage<?> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("count", data.getTotal());
        result.put("data", data.getRecords());
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
