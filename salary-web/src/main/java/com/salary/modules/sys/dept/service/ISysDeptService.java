package com.salary.modules.sys.dept.service;

import com.salary.core.R;
import com.salary.modules.sys.dept.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 查询主页菜单
     * @return
     */
    public List<SysDept> queryIndexDepts(Integer id);

    List<SysDept> listByOrderSort();
    /**
     * 查询ztree数据
     * @return
     */
    public List<Map<String,Object>> queryZtree();

    /**
     * 删除菜单
     * @param ids
     * @return
     */
    public boolean delDept(String ids);


    /**
     * 保存菜单
     * @param sysDept
     * @return
     */
    public R deptSave(SysDept sysDept);
}
