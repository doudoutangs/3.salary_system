package com.salary.modules.sys.role.service.impl;

import com.salary.modules.sys.role.entity.SysUserRole;
import com.salary.modules.sys.role.mapper.SysUserRoleMapper;
import com.salary.modules.sys.role.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-09
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

}
