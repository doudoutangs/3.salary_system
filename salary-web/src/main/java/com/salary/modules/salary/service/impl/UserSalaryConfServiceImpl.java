package com.salary.modules.salary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salary.modules.salary.entity.UserSalaryConf;
import com.salary.modules.salary.mapper.UserSalaryConfMapper;
import com.salary.modules.salary.service.IUserSalaryConfService;
import com.salary.modules.sys.dict.entity.SysDict;
import com.salary.modules.sys.dict.mapper.SysDictMapper;
import com.salary.modules.sys.dict.service.ISysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
@Service("userSalaryConfService")
@Transactional
public class UserSalaryConfServiceImpl extends ServiceImpl<UserSalaryConfMapper, UserSalaryConf> implements IUserSalaryConfService {

}
