package com.salary.modules.sys.log.service.impl;

import com.salary.modules.sys.log.entity.SysBizLog;
import com.salary.modules.sys.log.mapper.SysBizLogMapper;
import com.salary.modules.sys.log.service.ISysBizLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务日志 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-10
 */
@Service("sysBizLogService")
@Transactional
public class SysBizLogServiceImpl extends ServiceImpl<SysBizLogMapper, SysBizLog> implements ISysBizLogService {

}
