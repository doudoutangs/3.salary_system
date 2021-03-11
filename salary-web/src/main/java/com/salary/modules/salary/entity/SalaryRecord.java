package com.salary.modules.salary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.salary.core.Page;
import com.salary.annotation.Excel;

@TableName("b_salary_record")
public class SalaryRecord extends Page {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer employeeId;

    @TableField(exist = false)
    @Excel(name = "员工姓名")
    private String userName;
    @TableField(exist = false)
    @Excel(name = "员工工号")
    private String userNo;

    @Excel(name = "工资月份")
    private String salaryDate;
    @Excel(name = "应发工资")
    private Float mustSalary;
    @Excel(name = "实发工资")
    private Float realitySalary;

    private String createTime;

    private String updateTime;

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

    public String getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(String salaryDate) {
        this.salaryDate = salaryDate;
    }

    public Float getMustSalary() {
        return mustSalary;
    }

    public void setMustSalary(Float mustSalary) {
        this.mustSalary = mustSalary;
    }

    public Float getRealitySalary() {
        return realitySalary;
    }

    public void setRealitySalary(Float realitySalary) {
        this.realitySalary = realitySalary;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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

    @Override
    public String toString() {
        return "SalaryRecord{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", userNo=" + userNo +
                ", userName=" + userName +
                ", salaryDate='" + salaryDate + '\'' +
                ", mustSalary=" + mustSalary +
                ", realitySalary=" + realitySalary +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}