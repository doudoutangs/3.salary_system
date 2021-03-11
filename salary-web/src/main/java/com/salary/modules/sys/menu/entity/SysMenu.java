package com.salary.modules.sys.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class SysMenu implements Serializable {

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
     * 菜单地址
     */
    private String url;

    /**
     * 菜单权限
     */
    private String role;

    /**
     * 是不是菜单
     */
    @TableField("is_menu")
    private String isMenu;

    /**
     * 菜单图标(只限一级菜单使用)
     */
    private String icon;

    /**
     * 菜单排序
     */
    private String sort;

    /**
     * 菜单层级
     */
    private String level;

    @TableField(exist = false)
    private List<SysMenu> childrens;

    public List<SysMenu> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<SysMenu> childrens) {
        this.childrens = childrens;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "id=" + id +
                ", pId=" + pId +
                ", name=" + name +
                ", url=" + url +
                ", role=" + role +
                ", isMenu=" + isMenu +
                ", icon=" + icon +
                ", sort=" + sort +
                ", level=" + level +
                "}";
    }
}
