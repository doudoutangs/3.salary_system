package com.salary.modules.sys.dict.service.impl;

import com.salary.modules.sys.dict.entity.SysDict;
import com.salary.modules.sys.dict.mapper.SysDictMapper;
import com.salary.modules.sys.dict.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service("sysDictService")
@Transactional
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

}
