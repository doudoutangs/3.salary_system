package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.SalaryConf;
import com.salary.modules.salary.service.ISalaryConfService;
import com.salary.modules.sys.dict.entity.SysDict;
import com.salary.modules.sys.dict.service.ISysDictService;
import com.salary.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: QQ:553039957
 * @Date: 2023/9/25 15:41
 * @Description:
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 
 */
@Controller
@RequestMapping("/salary/conf")
public class SalaryConfController {

    private static final String PREFIX = "modules/salary/conf/";

    @Autowired
    private ISalaryConfService salaryConfService;
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:conf:index")
    public String index(Model model) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("pid", "102");
        Collection<SysDict> types = sysDictService.listByMap(p);
        model.addAttribute("types", types);
        p.put("pid", "115");
        Collection<SysDict> rules = sysDictService.listByMap(p);
        model.addAttribute("rules", rules);
        return PREFIX + "conf_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        SalaryConf record = ControllerUtils.bindParams(request, SalaryConf.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");

        IPage<SalaryConf> page = salaryConfService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        return R.return4Page(page);
    }


    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "salary:conf:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("pid", "86");
        Collection<SysDict> typeNames = sysDictService.listByMap(p);
        model.addAttribute("typeNames", typeNames);
        p.put("pid", "102");
        Collection<SysDict> types = sysDictService.listByMap(p);
        model.addAttribute("types", types);
        p.put("pid", "115");
        Collection<SysDict> rules = sysDictService.listByMap(p);
        model.addAttribute("rules", rules);
        return PREFIX + "conf_add.html";
    }

    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Req4Json(value = "/save", title = "保存", parameters = "id")
    public Object save(SalaryConf param) {
        boolean save = salaryConfService.save(param);
        if (save) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 修改页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/edit")
    @NeedAuth(value = "salary:conf:edit")
    public String edit(Model model, Integer id) {
        SalaryConf params = salaryConfService.getById(id);
        model.addAttribute("params", params);
        return PREFIX + "conf_edit.html";
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/update", title = "更新", parameters = "id")
    public Object update(SalaryConf record) {
        record.setType(null);
        boolean update = salaryConfService.updateById(record);
        if (update) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Req4Json(value = "/del", title = "删除", parameters = "id")
    public Object del(Integer id) {
        boolean del = salaryConfService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
