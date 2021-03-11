package com.salary.modules.salary.service.impl;

import com.salary.modules.salary.entity.SalaryConf;
import com.salary.modules.salary.mapper.SalaryConfMapper;
import com.salary.modules.salary.service.ISalaryConfService;
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
@Service("salaryConfService")
@Transactional
public class SalaryConfServiceImpl extends ServiceImpl<SalaryConfMapper, SalaryConf> implements ISalaryConfService {

}
