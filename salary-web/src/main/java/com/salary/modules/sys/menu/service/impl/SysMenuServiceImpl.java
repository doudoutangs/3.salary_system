package com.salary.modules.sys.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salary.contants.RoleEnum;
import com.salary.core.R;
import com.salary.modules.sys.menu.entity.SysMenu;
import com.salary.modules.sys.role.entity.SysRole;
import com.salary.modules.sys.role.entity.SysRoleMenu;
import com.salary.modules.sys.role.entity.SysUserRole;
import com.salary.modules.sys.menu.mapper.SysMenuMapper;
import com.salary.modules.sys.role.mapper.SysRoleMenuMapper;
import com.salary.modules.sys.menu.service.ISysMenuService;
import com.salary.modules.sys.role.service.ISysRoleService;
import com.salary.modules.sys.role.service.ISysUserRoleService;
import com.salary.modules.sys.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
@Service("sysMenuService")
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> queryIndexMenus(Integer id) {
        List<SysMenu> menus4Top = new ArrayList<>();
        QueryWrapper userRole = new QueryWrapper();
        userRole.eq("user_id", id);
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(userRole);
        Iterator<SysUserRole> iterator = sysUserRoleList.iterator();
        while (iterator.hasNext()) {
            SysUserRole sysUserRole = iterator.next();
            SysRole sysRole = sysRoleService.getById(sysUserRole.getRoleId());
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
                SysMenu sysMenu = this.getById(sysRoleMenu.getMenuId());
                if (sysMenu != null) {
                    menus4Top.add(sysMenu);
                }
            }
        }
        for (SysMenu sysMenu : menus4Top) {
            List<SysMenu> children = new ArrayList<>();
            queryMenuChildren(sysMenu, children, menus4Top);
            sysMenu.setChildrens(children);
        }

        Iterator<SysMenu> it = menus4Top.iterator();
        while (it.hasNext()) {
            SysMenu sysMenu = it.next();
            if (sysMenu.getpId() != 0) {
                it.remove();
            }
        }
        return menus4Top;
    }

    @Override
    public List<SysMenu> listByOrderSort() {
        QueryWrapper q = new QueryWrapper();
        q.orderByAsc("sort");
        return this.list(q);
    }

    private void queryMenuChildren(SysMenu sysMenu, List<SysMenu> children, List<SysMenu> old) {
        if (sysMenu.getpId() == 0) {
            for (SysMenu child : old) {
                if (child.getpId() == sysMenu.getId()) {
                    List<SysMenu> nextChildren = new ArrayList<>();
                    queryMenuChildren(child, nextChildren, old);
                    child.setChildrens(nextChildren);
                    children.add(child);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> queryZtree() {
        List<SysMenu> sysMenuList = listByOrderSort();
        List<Map<String, Object>> list = new LinkedList<>();
        for (SysMenu sysMenu : sysMenuList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", sysMenu.getId());
            map.put("pId", sysMenu.getpId());
            map.put("name", sysMenu.getName());
            list.add(map);
        }
        return list;
    }


    @Override
    public boolean delMenu(String ids) {
        List<String> ids4List = Arrays.asList(ids.split(","));
        for (String id : ids4List) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("p_id", id);
            List<SysMenu> children = this.list(queryWrapper);
//            this.log.debug("id = " + id + ", children size = " + children.size());
            if (children.size() > 0) {
                List<Integer> childrenIds = new ArrayList<>();
                for (SysMenu child : children) {
                    childrenIds.add(child.getId());
                }
                this.removeByIds(childrenIds);
            }
            this.removeById(id);
        }
        return true;
    }

    @Override
    public R menuSave(SysMenu sysMenu) {
        if (sysMenu.getpId() == 0) {
            sysMenu.setLevel("1");
        } else {
            SysMenu parent = this.getById(sysMenu.getpId());
            if (parent == null) {
                return R.error("父级菜单不存在");
            }
            if (parent.getLevel().equals("2")) {
                sysMenu.setLevel("3");
            } else if (parent.getLevel().equals("1")) {
                sysMenu.setLevel("2");
            } else {
                return R.error("菜单只支持到三级");
            }
        }
        this.save(sysMenu);
        return R.success("添加成功");
    }
}
