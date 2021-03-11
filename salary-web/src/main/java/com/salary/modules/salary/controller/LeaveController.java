package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.Leave;
import com.salary.modules.salary.entity.WorkRecord;
import com.salary.modules.salary.service.ILeaveService;
import com.salary.modules.salary.service.IWorkRecordService;
import com.salary.modules.sys.dict.entity.SysDict;
import com.salary.modules.sys.dict.service.ISysDictService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
import com.salary.util.DateUtils;
import com.salary.util.ShiroUtil;
import com.salary.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
@Controller
@RequestMapping("/salary/leave")
public class LeaveController {

    private static final String PREFIX = "modules/salary/leave/";

    @Autowired
    private ILeaveService leaveService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDictService sysDictService;
    @Autowired
    private IWorkRecordService workRecordService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:leave:index")
    public String index(Model model) {
        return PREFIX + "leave_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        Leave record = ControllerUtils.bindParams(request, Leave.class);
        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");
        queryWrapper.like(StringUtils.isNotBlank(record.getLeaveDate()), "leaveDate", record.getLeaveDate());
        IPage<Leave> page = leaveService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        List<Leave> records = page.getRecords();
        List<Leave> list = new ArrayList<Leave>();
        for (Leave r : records) {
            SysUser leaveUser = sysUserService.getById(r.getLeaveId());
            r.setLeaveName(leaveUser.getUserNick());
            if (null != r.getApprovalId()) {
                SysUser user = sysUserService.getById(r.getApprovalId());
                r.setApprovalName(user.getUserNick());
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
    @NeedAuth(value = "salary:leave:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        return PREFIX + "leave_add.html";
    }

    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Req4Json(value = "/save", title = "保存", parameters = "id")
    public Object save(Leave param) {
        Integer id = ShiroUtil.getUser().getId();
        param.setLeaveId(id);
        param.setCreateTime(new Date());
        boolean save = leaveService.save(param);
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
    @NeedAuth(value = "salary:leave:edit")
    public String edit(Model model, Integer id) {
        Leave params = leaveService.getById(id);
        model.addAttribute("params", params);
        return PREFIX + "leave_edit.html";
    }

    @Req4Model(value = "/approval")
    @NeedAuth(value = "salary:leave:approval")
    public String approval(Model model, Integer id) {
        Leave params = leaveService.getById(id);
        model.addAttribute("params", params);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("pid", "129");
        Collection<SysDict> list = sysDictService.listByMap(p);
        model.addAttribute("approvalResults", list);
        return PREFIX + "leave_approval.html";
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/update", title = "更新", parameters = "id")
    public Object update(Leave record) {
        if (null != record.getApprovalResult()) {
            Integer id = ShiroUtil.getUser().getId();
            record.setApprovalId(id);
            record.setApprovalTime(DateUtils.getTime());
            if (1 == record.getApprovalResult()) {
                WorkRecord r = new WorkRecord();
                r.setEmployeeId(record.getLeaveId());
                r.setWorkDate(record.getLeaveDate());
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.setEntity(r);
                WorkRecord one = workRecordService.getOne(queryWrapper);
                if (null != one) {
                    return R.error("当天已有打卡记录");
                }
                //请假通过，考勤增加请假记录
                WorkRecord workRecord = new WorkRecord();
                workRecord.setWorkDate(record.getLeaveDate());
                workRecord.setWorkMonth(record.getLeaveDate().substring(0, 7));
                workRecord.setWorkStatus(5);
                workRecord.setEmployeeId(record.getLeaveId());
                workRecordService.save(workRecord);
            }
        }
        boolean update = leaveService.updateById(record);
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
        boolean del = leaveService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
