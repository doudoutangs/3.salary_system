package com.salary.modules.sys.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.contants.RoleEnum;
import com.salary.core.R;
import com.salary.modules.sys.role.entity.SysRole;
import com.salary.modules.sys.role.service.ISysRoleService;
import com.salary.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-08
 */
@Controller
@RequestMapping("/sys/sysRole")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    private static final String PREFIX = "modules/sys/role/";


    /**
     * 角色主页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return PREFIX + "role_index.html";
    }


    /**
     * 角色添加页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/role_add")
    public String role_add(Model model) {
        model.addAttribute("roleEnum", RoleEnum.values());
        return PREFIX + "role_add.html";
    }

    /**
     * 保存
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/role_save")
    @ResponseBody
    public Object roleSave(SysRole sysRole) {
        boolean save = this.sysRoleService.save(sysRole);
        if (save) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 查询集合
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request) throws Exception {
        SysRole sysRole = ControllerUtils.bindParams(request, SysRole.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(sysRole);
        IPage<SysRole> sysRoleList = sysRoleService.page(new Page<>(sysRole.getPage(), sysRole.getLimit()), queryWrapper);
        return R.return4Page(sysRoleList);
    }

    /**
     * 角色修改页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/role_edit")
    public String role_edit(Model model, Integer id) {
        SysRole sysRole = this.sysRoleService.getById(id);
        model.addAttribute("roleEnum", RoleEnum.values());
        model.addAttribute("sysRole", sysRole);
        return PREFIX + "role_edit.html";
    }

    /**
     * 保存
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/role_update")
    @ResponseBody
    public Object roleUpdate(SysRole sysRole) {
        boolean update = this.sysRoleService.updateById(sysRole);
        if (update) {
            return R.success("更新成功");
        }
        return R.error("更新失败");
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delRole")
    @ResponseBody
    public Object delRole(Integer id) {
        boolean del = sysRoleService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * 角色分配权限页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/role_menu")
    public String role_menu(Model model, Integer id) {
        SysRole sysRole = this.sysRoleService.getById(id);
        model.addAttribute("sysRole", sysRole);
        return PREFIX + "role_menu.html";
    }

    /**
     * 根据用户角色查询菜单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryMenus4Role")
    @ResponseBody
    public Object queryMenus4Role(Integer id) {
        return sysRoleService.queryMenus4Role(id);
    }


    /**
     * 更新角色权限
     *
     * @param id
     * @param ids
     * @return
     */
    @RequestMapping(value = "/saveMenus4Role")
    @ResponseBody
    public Object saveMenus4Role(Integer id, String ids) {
        boolean flag = sysRoleService.saveMenus4Role(id, ids);
        if (flag) {
            return R.success("保存成功");
        }
        return R.error("保存失败");

    }
}
