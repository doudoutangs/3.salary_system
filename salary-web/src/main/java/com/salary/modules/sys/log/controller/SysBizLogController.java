package com.salary.modules.sys.log.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.sys.log.entity.SysBizLog;
import com.salary.modules.sys.log.service.ISysBizLogService;
import com.salary.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 业务日志 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-10
 */
@Controller
@RequestMapping("/sys/sysBizLog")
public class SysBizLogController {

    private static final String PREFIX = "modules/sys/log/";

    @Autowired
    private ISysBizLogService sysBizLogService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "sys:log:index")
    public String index(Model model) {
        return PREFIX + "log_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        SysBizLog sysBizLog = ControllerUtils.bindParams(request, SysBizLog.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(sysBizLog);
        IPage<SysBizLog> page = sysBizLogService.page(new Page<>(sysBizLog.getPage(), sysBizLog.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "sys:log:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        return PREFIX + "log_add.html";
    }

    /**
     * 保存
     *
     * @param sysBizLog
     * @return
     */
    @Req4Json(value = "/save", title = "保存业务日志", parameters = "id")
    public Object save(SysBizLog sysBizLog) {
        boolean save = sysBizLogService.save(sysBizLog);
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
    @NeedAuth(value = "sys:log:edit")
    public String edit(Model model, Integer id) {
        SysBizLog sysBizLog = sysBizLogService.getById(id);
        model.addAttribute("sysBizLog", sysBizLog);
        return PREFIX + "log_edit.html";
    }

    /**
     * 更新
     *
     * @param sysBizLog
     * @return
     */
    @Req4Json(value = "/update", title = "更新业务日志", parameters = "id")
    public Object update(SysBizLog sysBizLog) {
        boolean update = sysBizLogService.updateById(sysBizLog);
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
    @Req4Json(value = "/del", title = "删除业务日志", parameters = "id")
    public Object del(Integer id) {
        boolean del = sysBizLogService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
