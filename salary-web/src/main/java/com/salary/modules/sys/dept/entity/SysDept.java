package com.salary.modules.sys.dept.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.salary.modules.sys.menu.entity.SysMenu;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级菜单
     */
    @TableField("p_id")
    private Integer pId;

    /**
     * 菜单名称
     */
    private String name;


    /**
     * 菜单排序
     */
    private Integer orderNum;
    @TableField(exist = false)
    private List<SysDept> childrens;

    public List<SysDept> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<SysDept> childrens) {
        this.childrens = childrens;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
