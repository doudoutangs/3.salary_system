package com.salary.modules.sys.dept.controller;


import com.salary.core.R;
import com.salary.modules.sys.dept.entity.SysDept;
import com.salary.modules.sys.dept.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 系统部门表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
@Controller
@RequestMapping("/sys/sysDept")
public class SysDeptController {

    private static final String PREFIX = "modules/sys/dept/";

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 部门主页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        return PREFIX + "dept_index.html";
    }

    /**
     * 查询部门
     *
     * @return
     */
    @RequestMapping(value = "/queryDepts")
    @ResponseBody
    public Object queryDepts() {
        return sysDeptService.list();
    }

    /**
     * 查询ztree数据
     * @return
     */
    @RequestMapping(value = "/queryZtree")
    @ResponseBody
    public Object queryZtree(){
        return sysDeptService.queryZtree();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delDept")
    @ResponseBody
    public Object delDept(String ids) {
        boolean del = sysDeptService.delDept(ids);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }


    /**
     * 添加部门页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/dept_add")
    public String deptAdd(Model model) {
        return PREFIX + "dept_add.html";
    }

    /**
     * 保存部门
     * @param sysDept
     * @return
     */
    @RequestMapping(value = "/dept_save")
    @ResponseBody
    public R deptSave(SysDept sysDept) {
        return sysDeptService.deptSave(sysDept);
    }

    /**
     * 编辑部门页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/dept_edit")
    public String deptEdit(Model model,Integer id) {
        SysDept sysDept = sysDeptService.getById(id);
        model.addAttribute("sysDept",sysDept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 更新部门
     * @param sysDept
     * @return
     */
    @RequestMapping(value = "/dept_update")
    @ResponseBody
    public R deptUpdate(SysDept sysDept) {
        boolean update = sysDeptService.updateById(sysDept);
        if (update) {
            return R.success("更新成功");
        }
        return R.error("更新失败");
    }
}
