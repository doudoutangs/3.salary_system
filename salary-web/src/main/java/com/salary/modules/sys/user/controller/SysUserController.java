package com.salary.modules.sys.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.contants.*;
import com.salary.core.R;
import com.salary.modules.sys.role.entity.SysUserRole;
import com.salary.modules.sys.role.service.ISysRoleService;
import com.salary.modules.sys.role.service.ISysUserRoleService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
import com.salary.util.CryptoUtils;
import com.salary.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-09
 */
@Controller
@RequestMapping("/sys/sysUser")
public class SysUserController {

    private static final String PREFIX = "modules/sys/user/";

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    @NeedAuth(value = "sys:user:index")
    public String index(Model model) {
        model.addAttribute("status", UserStatusEnum.values());
        model.addAttribute("sex", UserSexEnum.values());
        return PREFIX + "user_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request) throws Exception {
        SysUser sysUser = ControllerUtils.bindParams(request, SysUser.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(sysUser);
        queryWrapper.orderByDesc("id");
        IPage<SysUser> page = sysUserService.page(new Page<>(sysUser.getPage(), sysUser.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    @NeedAuth(value = "sys:user:add")
    public String add(Model model) {
        model.addAttribute("status", UserStatusEnum.values());
        model.addAttribute("sex", UserSexEnum.values());
        model.addAttribute("userType", UserTypeEnum.values());
        model.addAttribute("userLevel", UserLevelEnum.values());
        model.addAttribute("post", UserPostEnum.values());
        model.addAttribute("education", UserEducationEnum.values());
        QueryWrapper role = new QueryWrapper();
        role.eq("status", RoleEnum.ENABLE.getCode());
        model.addAttribute("roles", sysRoleService.list(role));
        return PREFIX + "user_add.html";
    }

    /**
     * 保存
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(SysUser sysUser) {
        sysUser.setUserPwd(CryptoUtils.generate(sysUser.getUserPwd()));
        boolean save = sysUserService.save(sysUser);
        handleUserRole(sysUser);
        if (save) {
            sysUser.setUserNo(UserUtil.getUserNo(sysUser.getId()));
            sysUserService.updateById(sysUser);
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }
    /**
     * 修改页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    @NeedAuth(value = "sys:user:edit")
    public String edit(Model model, Integer id) {
        SysUser sysUser = sysUserService.getById(id);
        model.addAttribute("sysUser", sysUser);
        model.addAttribute("roles", sysUserService.getRole4User(id));
        model.addAttribute("status", UserStatusEnum.values());
        model.addAttribute("sex", UserSexEnum.values());
        return PREFIX + "user_edit.html";
    }

    /**
     * 更新
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysUser sysUser) throws Exception {

        String userPwd = sysUser.getUserPwd();
        if(StringUtils.isNotBlank(userPwd)){
            sysUser.setUserPwd(CryptoUtils.generate(userPwd));
        }
        boolean update = sysUserService.updateById(sysUser);
        handleUserRole(sysUser);
        if (update) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }
    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del(Integer id) {
        boolean del = sysUserService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    private void handleUserRole(SysUser sysUser) {
        QueryWrapper delUserRole = new QueryWrapper();
        delUserRole.eq("user_id", sysUser.getId());
        sysUserRoleService.remove(delUserRole);
        for (String roleId : sysUser.getRoleIds().split(",")) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(Integer.valueOf(roleId));
            sysUserRole.setUserId(sysUser.getId());
            sysUserRoleService.save(sysUserRole);
        }
    }

}
