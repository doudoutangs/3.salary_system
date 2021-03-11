package com.salary.modules.sys.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.salary.contants.RoleEnum;
import com.salary.core.R;
import com.salary.modules.sys.dept.entity.SysDept;
import com.salary.modules.sys.dept.mapper.SysDeptMapper;
import com.salary.modules.sys.dept.service.ISysDeptService;
import com.salary.modules.sys.role.entity.SysRole;
import com.salary.modules.sys.role.entity.SysUserRole;
import com.salary.modules.sys.role.service.ISysRoleService;
import com.salary.modules.sys.role.service.ISysUserRoleService;
import com.salary.modules.sys.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
@Service("sysDeptService")
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public List<SysDept> queryIndexDepts(Integer id) {
        List<SysDept> menus4Top = new ArrayList<>();
        QueryWrapper userRole = new QueryWrapper();
        userRole.eq("user_id", id);
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(userRole);
        Iterator<SysUserRole> iterator = sysUserRoleList.iterator();
        while (iterator.hasNext()) {
            SysUserRole sysUserRole = iterator.next();
            SysRole sysRole = sysRoleService.getById(sysUserRole.getRoleId());
            if (sysRole != null) {
                if (sysRole.getStatus().equals(RoleEnum.UNENABLE.getCode())) {
                    iterator.remove();
                }
            } else {
                iterator.remove();
            }
        }
        for (SysDept sysDept : menus4Top) {
            List<SysDept> children = new ArrayList<>();
            queryDeptChildren(sysDept, children, menus4Top);
            sysDept.setChildrens(children);
        }

        Iterator<SysDept> it = menus4Top.iterator();
        while (it.hasNext()) {
            SysDept sysDept = it.next();
            if (sysDept.getpId() != 0) {
                it.remove();
            }
        }
        return menus4Top;
    }

    @Override
    public List<SysDept> listByOrderSort() {
        QueryWrapper q = new QueryWrapper();
        q.orderByAsc("sort");
        return this.list(q);
    }

    private void queryDeptChildren(SysDept sysDept, List<SysDept> children, List<SysDept> old) {
        if (sysDept.getpId() == 0) {
            for (SysDept child : old) {
                if (child.getpId() == sysDept.getId()) {
                    List<SysDept> nextChildren = new ArrayList<>();
                    queryDeptChildren(child, nextChildren, old);
                    child.setChildrens(nextChildren);
                    children.add(child);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> queryZtree() {
        List<SysDept> sysDeptList = listByOrderSort();
        List<Map<String, Object>> list = new LinkedList<>();
        for (SysDept sysDept : sysDeptList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", sysDept.getId());
            map.put("pId", sysDept.getpId());
            map.put("name", sysDept.getName());
            list.add(map);
        }
        return list;
    }


    @Override
    public boolean delDept(String ids) {
        List<String> ids4List = Arrays.asList(ids.split(","));
        for (String id : ids4List) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("p_id", id);
            List<SysDept> children = this.list(queryWrapper);
//            this.log.debug("id = " + id + ", children size = " + children.size());
            if (children.size() > 0) {
                List<Integer> childrenIds = new ArrayList<>();
                for (SysDept child : children) {
                    childrenIds.add(child.getId());
                }
                this.removeByIds(childrenIds);
            }
            this.removeById(id);
        }
        return true;
    }

    @Override
    public R deptSave(SysDept sysDept) {
        this.save(sysDept);
        return R.success("添加成功");
    }
}
