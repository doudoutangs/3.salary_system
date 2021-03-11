package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.SalaryConf;
import com.salary.modules.salary.entity.SalaryDetail;
import com.salary.modules.salary.service.ISalaryDetailService;
import com.salary.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
@Controller
@RequestMapping("/salary/detail")
public class SalaryDetailController {

    private static final String PREFIX = "modules/salary/detail/";

    @Autowired
    private ISalaryDetailService salaryDetailService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:detail:index")
    public String index(Model model, Integer id) {
        model.addAttribute("recordId", id);
        return PREFIX + "detail_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
//    @RequestMapping(value = "/queryList")
//    @ResponseBody
    public Object queryList(HttpServletRequest request) throws Exception {
//        SalaryDetail detail = ControllerUtils.bindParams(request, SalaryDetail.class);
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.setEntity(detail);
//        List<SalaryDetail> list = salaryDetailService.list(queryWrapper);
//        return list;

        SalaryDetail detail = ControllerUtils.bindParams(request, SalaryDetail.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(detail);
        queryWrapper.orderByDesc("id");

        IPage<SalaryConf> page = salaryDetailService.page(new Page<>(detail.getPage(), detail.getLimit()), queryWrapper);
        return R.return4Page(page);
    }


    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "salary:detail:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        return PREFIX + "detail_add.html";
    }

    /**
     * 添加字典类型
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "salary:detail:add:type")
    @Req4Model(value = "/add/type")
    public String addType(Model model) {
        return PREFIX + "detail_add_type.html";
    }

    /**
     * 保存
     *
     * @param salaryDetail
     * @return
     */
    @Req4Json(value = "/save", title = "保存字典表", parameters = "id")
    public Object save(SalaryDetail salaryDetail) {
        boolean save = salaryDetailService.save(salaryDetail);
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
    @NeedAuth(value = "salary:detail:edit")
    public String edit(Model model, Integer id) {
        SalaryDetail salaryDetail = salaryDetailService.getById(id);
        model.addAttribute("salaryDetail", salaryDetail);
        return PREFIX + "detail_edit.html";
    }

    /**
     * 更新
     *
     * @param salaryDetail
     * @return
     */
    @Req4Json(value = "/update", title = "更新字典表", parameters = "id")
    public Object update(SalaryDetail salaryDetail) {
        boolean update = salaryDetailService.updateById(salaryDetail);
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
    @Req4Json(value = "/del", title = "删除字典表", parameters = "id")
    public Object del(Integer id) {
        boolean del = salaryDetailService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
