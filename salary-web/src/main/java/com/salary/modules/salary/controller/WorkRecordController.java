package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.WorkRecord;
import com.salary.modules.salary.service.IWorkRecordService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
import com.salary.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/salary/work/record")
public class WorkRecordController {

    private static final String PREFIX = "modules/salary/work/record/";
    @Value("${up_time}")
    String standardUpTime;
    @Value("${down_time}")
    String standardDownTime;
    @Value("${over_time}")
    String standardOverTime;
    @Autowired
    private IWorkRecordService workRecordService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:work:record:index")
    public String index(Model model) {
        Integer id = ShiroUtil.getUser().getId();
        List<SysUser> users = new ArrayList<SysUser>();
        if (id != 1) {
            SysUser user = sysUserService.getById(id);
            users.add(user);
        } else {
            users = sysUserService.list();
        }
        model.addAttribute("users", users);
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
        WorkRecord record = ControllerUtils.bindParams(request, WorkRecord.class);
        Integer id = ShiroUtil.getUser().getId();
        if (id != 1) {
            record.setEmployeeId(id);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");
        IPage<WorkRecord> page = workRecordService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        //设置员工姓名和工号
        List<WorkRecord> records = page.getRecords();
        List<WorkRecord> list = new ArrayList<WorkRecord>();
        for (WorkRecord r : records) {

            SysUser user = sysUserService.getById(r.getEmployeeId());
            if (null != user) {
                r.setUserNo(user.getUserNo());
                r.setUserName(user.getUserNick());
            }
            list.add(r);
        }

        page.setRecords(list);
        return R.return4Page(page);
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "salary:work:record:upTime")
    @Req4Model(value = "/upTime")
    public String upTime(Model model) {
        return PREFIX + "record_add.html";
    }

    /**
     * 保存
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/save", title = "保存", parameters = "id")
    public Object save(WorkRecord record) {
        Integer userId = ShiroUtil.getUser().getId();
        WorkRecord r = new WorkRecord();
        r.setEmployeeId(userId);
        r.setWorkDate(record.getWorkDate());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(r);


        WorkRecord one = workRecordService.getOne(queryWrapper);
        if (null != one) {
            return R.success(record.getWorkDate() + "上班已打卡，请勿重复打卡");
        }
        int upTime = Integer.valueOf(record.getUpTime().substring(0, 2));
        Integer uTime = Integer.valueOf(standardUpTime);
        Integer minute = Integer.valueOf(record.getUpTime().substring(3, 5));
        if (upTime > uTime || (upTime == uTime && minute > 0)) {
            //迟到
            record.setWorkStatus(2);
        }
        record.setEmployeeId(userId);
        record.setWorkMonth(record.getWorkDate().substring(0, 7));
        boolean save = workRecordService.save(record);
        if (save) {
            return R.success("打卡成功");
        }
        return R.error("打卡失败");
    }

    /**
     * 修改页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/downTime")
    @NeedAuth(value = "salary:work:record:downTime")
    public String downTime(Model model, Integer id) {
        model.addAttribute("id", id);
        return PREFIX + "record_edit.html";
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/update", title = "更新", parameters = "id")
    public Object update(WorkRecord record) {
        if (null == record.getId()) {
            return R.success("请先打" + record.getWorkDate() + "上班卡,再打下班卡");
        }
        int downTime = Integer.valueOf(record.getDownTime().substring(0, 2));
        if (downTime < Integer.valueOf(standardDownTime)) {
            //早退
            record.setWorkStatus(3);
        } else if (downTime >= Integer.valueOf(standardOverTime)) {
            //加班
            record.setWorkStatus(4);
        }
        record.setEmployeeId(ShiroUtil.getUser().getId());
        boolean update = workRecordService.updateById(record);
        if (update) {
            return R.success("打卡成功");
        }
        return R.error("打卡失败");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Req4Json(value = "/del", title = "删除", parameters = "id")
    public Object del(Integer id) {
        boolean del = workRecordService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
