package com.salary.modules.salary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.salary.core.Page;

import java.util.Date;
/**
 * @author: QQ:553039957
 * @Date: 2023/9/25 15:42
 * @Description:
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 
 */
@TableName("b_user_salary_conf")
public class UserSalaryConf  extends Page {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer employeeId;
    @TableField(exist = false)
    private String userNo;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String salaryItem;

    private Integer salaryConfId;

    private Float salaryConfRatio;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getSalaryConfId() {
        return salaryConfId;
    }

    public void setSalaryConfId(Integer salaryConfId) {
        this.salaryConfId = salaryConfId;
    }

    public Float getSalaryConfRatio() {
        return salaryConfRatio;
    }

    public void setSalaryConfRatio(Float salaryConfRatio) {
        this.salaryConfRatio = salaryConfRatio;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalaryItem() {
        return salaryItem;
    }

    public void setSalaryItem(String salaryItem) {
        this.salaryItem = salaryItem;
    }
}