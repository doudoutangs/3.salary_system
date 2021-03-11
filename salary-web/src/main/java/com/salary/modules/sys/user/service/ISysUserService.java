package com.salary.modules.sys.user.service;

import com.salary.modules.sys.role.entity.SysRole;
import com.salary.modules.sys.user.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author sun
 * @since 2019-05-09
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 查有角色的用户
     * @param id
     * @return
     */
    public List<SysRole> getRole4User(Integer id);


    /**
     * 根据用户id查询权限
     * @param id
     * @return
     */
    public List<String> queryAuth4User(Integer id);

}
