package com.salary.modules.sys.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.salary.core.Page;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
public class SysDict extends Page {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 排序
     */
    private Integer num;

    /**
     * 父级字典
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SysDict{" +
                "id=" + id +
                ", num=" + num +
                ", pid=" + pid +
                ", name=" + name +
                ", code=" + code +
                "}";
    }
}
