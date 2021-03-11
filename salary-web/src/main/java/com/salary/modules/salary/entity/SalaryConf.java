package com.salary.modules.salary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.salary.core.Page;

@TableName("b_salary_conf")
public class SalaryConf extends Page {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private String calcRule;

    private String typeName;

    private Integer amount;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCalcRule() {
        return calcRule;
    }

    public void setCalcRule(String calcRule) {
        this.calcRule = calcRule;
    }
}