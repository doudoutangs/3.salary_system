package com.salary.modules.salary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salary.modules.salary.entity.SalaryCalc;
import com.salary.modules.salary.mapper.SalaryCalcMapper;
import com.salary.modules.salary.service.ISalaryCalcService;
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
@Service("salaryCalcService")
@Transactional
public class SalaryCalcServiceImpl extends ServiceImpl<SalaryCalcMapper, SalaryCalc> implements ISalaryCalcService {

}
