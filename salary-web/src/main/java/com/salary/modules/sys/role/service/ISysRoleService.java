package com.salary.modules.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.salary.modules.sys.role.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author sun
 * @since 2019-05-08
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户角色查询菜单
     *
     * @param id
     * @return
     */
    public List<Map<String, Object>> queryMenus4Role(Integer id);


    /**
     * 更新角色权限
     * @param id
     * @param ids
     * @return
     */
    public boolean saveMenus4Role(Integer id, String ids);

}
