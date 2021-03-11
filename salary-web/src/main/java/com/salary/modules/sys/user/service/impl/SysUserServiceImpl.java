package com.salary.modules.sys.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salary.contants.RoleEnum;
import com.salary.modules.sys.menu.entity.SysMenu;
import com.salary.modules.sys.menu.mapper.SysMenuMapper;
import com.salary.modules.sys.role.entity.SysRole;
import com.salary.modules.sys.role.entity.SysRoleMenu;
import com.salary.modules.sys.role.entity.SysUserRole;
import com.salary.modules.sys.role.mapper.SysRoleMapper;
import com.salary.modules.sys.role.mapper.SysRoleMenuMapper;
import com.salary.modules.sys.role.mapper.SysUserRoleMapper;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-09
 */
@Service("sysUserService")
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysRole> getRole4User(Integer id) {
        SysUser sysUser = this.getById(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", sysUser.getId());
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);
        QueryWrapper role = new QueryWrapper();
        role.eq("status", RoleEnum.ENABLE.getCode());
        List<SysRole> sysRoleList = sysRoleMapper.selectList(role);
        for (SysRole sysRole : sysRoleList) {
            for (SysUserRole sysUserRole : sysUserRoles) {
                if (sysRole.getId() == sysUserRole.getRoleId()) {
                    sysRole.setChecked("checked");
                }
            }
        }
        return sysRoleList;
    }

    @Override
    public List<String> queryAuth4User(Integer id) {
        List<String> result = new ArrayList<>();
        QueryWrapper userRole = new QueryWrapper();
        userRole.eq("user_id", id);
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(userRole);
        Iterator<SysUserRole> iterator = sysUserRoleList.iterator();
        while (iterator.hasNext()) {
            SysUserRole sysUserRole = iterator.next();
            QueryWrapper role = new QueryWrapper();
            role.eq("id", sysUserRole.getRoleId());
            SysRole sysRole = sysRoleMapper.selectOne(role);
            if (sysRole != null) {
                if (sysRole.getStatus().equals(RoleEnum.UNENABLE.getCode())) {
                    iterator.remove();
                }
            } else {
                iterator.remove();
            }
        }
        for (SysUserRole sysUserRole : sysUserRoleList) {
            QueryWrapper roleMenu = new QueryWrapper();
            roleMenu.eq("role_id", sysUserRole.getRoleId());
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(roleMenu);
            for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
                SysMenu sysMenu = sysMenuMapper.selectById(sysRoleMenu.getMenuId());
                if (sysMenu != null) {
                    result.addAll(Arrays.asList(sysMenu.getRole().split(",")));
                }
            }
        }
        return result;
    }
}
