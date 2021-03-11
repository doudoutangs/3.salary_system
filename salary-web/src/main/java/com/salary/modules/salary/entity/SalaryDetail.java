package com.salary.modules.salary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.salary.core.Page;
import com.salary.annotation.Excel;

import java.util.Date;
@TableName("b_salary_detail")
public class SalaryDetail  extends Page {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer recordId;


    private Integer costNo;
    @Excel(name = "项目名称")
    private String costName;
    @Excel(name = "项目类型")
    private String type;
    @Excel(name = "项目费用")
    private Float costAmount;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCostNo() {
        return costNo;
    }

    public void setCostNo(Integer costNo) {
        this.costNo = costNo;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public Float getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Float costAmount) {
        this.costAmount = costAmount;
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
}