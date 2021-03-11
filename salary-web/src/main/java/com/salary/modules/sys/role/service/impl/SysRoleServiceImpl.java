package com.salary.modules.sys.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.salary.modules.sys.role.entity.SysRole;
import com.salary.modules.sys.role.entity.SysRoleMenu;
import com.salary.modules.sys.role.mapper.SysRoleMapper;
import com.salary.modules.sys.role.mapper.SysRoleMenuMapper;
import com.salary.modules.sys.menu.service.ISysMenuService;
import com.salary.modules.sys.role.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-08
 */
@Service("sysRoleService")
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<Map<String, Object>> queryMenus4Role(Integer id) {
        List<Map<String,Object>> ztree = sysMenuService.queryZtree();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",id);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(queryWrapper);
        for(SysRoleMenu sysRoleMenu : sysRoleMenus){
            for(Map map : ztree){
                if(map.get("pId").equals(0)){
                    map.put("open",true);
                }
                if(map.get("id").equals(sysRoleMenu.getMenuId())){
                    map.put("checked",true);
                }
            }
        }
        return ztree;
    }

    @Override
    public boolean saveMenus4Role(Integer id, String ids) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",id);
        this.sysRoleMenuMapper.delete(queryWrapper);
        for(String s : ids.split(",")){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(Integer.valueOf(s));
            sysRoleMenu.setRoleId(id);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return true;
    }
}
