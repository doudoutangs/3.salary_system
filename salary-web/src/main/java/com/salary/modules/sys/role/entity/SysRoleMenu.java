package com.salary.modules.sys.role.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;

/**
* <p>
    * 角色和菜单表
    * </p>
*
* @author sun
* @since 2019-05-09
*/
    public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * id
            */
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer roleId;

    private Integer menuId;

        public Integer getId() {
        return id;
        }

            public void setId(Integer id) {
        this.id = id;
        }
        public Integer getRoleId() {
        return roleId;
        }

            public void setRoleId(Integer roleId) {
        this.roleId = roleId;
        }
        public Integer getMenuId() {
        return menuId;
        }

            public void setMenuId(Integer menuId) {
        this.menuId = menuId;
        }

    @Override
    public String toString() {
    return "SysRoleMenu{" +
            "id=" + id +
            ", roleId=" + roleId +
            ", menuId=" + menuId +
    "}";
    }
}
