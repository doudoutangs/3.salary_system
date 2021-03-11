package com.salary.modules.salary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salary.modules.salary.entity.WorkRecord;
import com.salary.modules.salary.mapper.WorkRecordMapper;
import com.salary.modules.salary.service.IWorkRecordService;
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
@Service("workRecordService")
@Transactional
public class WorkRecordServiceImpl extends ServiceImpl<WorkRecordMapper, WorkRecord> implements IWorkRecordService {

}
