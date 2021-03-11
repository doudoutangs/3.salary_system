package com.salary.modules.sys.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.salary.core.Page;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 * 业务日志
 * </p>
 *
 * @author sun
 * @since 2019-05-10
 */
public class SysBizLog extends Page {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 记录参数
     */
    private String params;

    /**
     * 执行类
     */
    private String className;

    /**
     * 执行方法
     */
    private String method;

    /**
     * 创建时间
     */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysBizLog{" +
                "id=" + id +
                ", title=" + title +
                ", params=" + params +
                ", className=" + className +
                ", method=" + method +
                ", createTime=" + createTime +
                "}";
    }
}
