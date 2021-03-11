package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.SalaryDetail;
import com.salary.modules.salary.entity.SalaryRecord;
import com.salary.modules.salary.service.ISalaryDetailService;
import com.salary.modules.salary.service.ISalaryRecordService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
import com.salary.util.ExcelUtil;
import com.salary.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
@Controller
@RequestMapping("/salary/record")
public class SalaryRecordController {

    private static final String PREFIX = "modules/salary/record/";

    @Autowired
    private ISalaryRecordService salaryRecordService;
    @Autowired
    private ISalaryDetailService salaryDetailService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:record:index")
    public String index(Model model) {
        Integer id = ShiroUtil.getUser().getId();
        List<SysUser> users = new ArrayList<SysUser>();
        if (id != 1) {
            SysUser user = sysUserService.getById(id);
            users.add(user);
        } else {
            users = sysUserService.list();
        }
        model.addAttribute("users",users);
        return PREFIX + "record_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        SalaryRecord record = ControllerUtils.bindParams(request, SalaryRecord.class);
        Integer id = ShiroUtil.getUser().getId();
        if (id != 1) {
            record.setEmployeeId(id);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");

        IPage<SalaryRecord> page = salaryRecordService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        List<SalaryRecord> records = page.getRecords();
        List<SalaryRecord> list = new ArrayList<SalaryRecord>();
        for (SalaryRecord r : records) {

            SysUser user = sysUserService.getById(r.getEmployeeId());
            r.setUserNo(user.getUserNo());
            r.setUserName(user.getUserNick());
            list.add(r);

        }
        page.setRecords(list);
        return R.return4Page(page);
    }

    private Integer getUserId(SysUser user) {
        QueryWrapper query = new QueryWrapper();
        query.setEntity(user);
        SysUser u = sysUserService.getOne(query);
        if(null == user){
            return null;
        }
        return u.getId();
    }
    /**
     * 导出Excel
     *
     * @param
     * @return
     */
//    @Req4Json(value = "/export", title = "导出Excel", parameters = "id")
    @GetMapping("/export")
    public Object export(HttpServletRequest request, HttpServletResponse response,Integer id) {
        SalaryRecord record = salaryRecordService.getById(id);
        SysUser user = sysUserService.getById(record.getEmployeeId());
        record.setUserNo(user.getUserNo());
        String userName = user.getUserNick();
        record.setUserName(userName);
        //查询明细
        SalaryDetail d = new SalaryDetail();
        d.setRecordId(id);
        QueryWrapper q = new QueryWrapper();
        q.setEntity(d);
        List<SalaryDetail> details = salaryDetailService.list(q);
        details =covert(details);
        List<SalaryRecord> list = new ArrayList<SalaryRecord>();
        list.add(record);

        // 下载文件的默认名称
        String fileName = userName+record.getSalaryDate()+"工资明细";
        try {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            ExcelUtil<SalaryDetail> util = new ExcelUtil<SalaryDetail>(SalaryDetail.class);
            OutputStream result = util.exportExcel(details, "工资明细",response);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success("导出成功");
    }
    private List<SalaryDetail> covert(List<SalaryDetail> list){
        List<SalaryDetail> details = new ArrayList<SalaryDetail>();
        for (SalaryDetail d : list) {
            d.setType(d.getType().equals("1")?"增加":"扣减");
            details.add(d);
        }
        return details;
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "salary:record:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
//        Collection<SalaryRecord> records = getParentDicts();
//        model.addAttribute("roots", records);
        return PREFIX + "record_add.html";
    }

    /**
     * 保存
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/save", title = "保存", parameters = "id")
    public Object save(SalaryRecord record) {
        boolean save = salaryRecordService.save(record);
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
    @NeedAuth(value = "salary:record:edit")
    public String edit(Model model, Integer id) {
        SalaryRecord record = salaryRecordService.getById(id);
        model.addAttribute("record", record);
        return PREFIX + "record_edit.html";
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/update", title = "更新", parameters = "id")
    public Object update(SalaryRecord record) {
        boolean update = salaryRecordService.updateById(record);
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
        boolean del = salaryDetailService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
