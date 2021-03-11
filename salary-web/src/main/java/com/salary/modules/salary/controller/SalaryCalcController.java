package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.*;
import com.salary.modules.salary.service.*;
import com.salary.modules.sys.dict.entity.SysDict;
import com.salary.modules.sys.dict.service.ISysDictService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
import com.salary.util.StringUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/salary/calc")
public class SalaryCalcController {

    private static final String PREFIX = "modules/salary/calc/";
    @Value("${up_time}")
    String standardUpTime;
    @Value("${down_time}")
    String standardDownTime;
    @Value("${over_time}")
    String standardOverTime;
    @Autowired
    private ISalaryCalcService salaryCalcService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IWorkRecordService workRecordService;
    @Autowired
    private IUserSalaryConfService userSalaryConfService;
    @Autowired
    private ISalaryConfService salaryConfService;
    @Autowired
    private ISalaryRecordService salaryRecordService;
    @Autowired
    private ISalaryDetailService salaryDetailService;
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:calc:index")
    public String index(Model model) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("pid", "118");
        Collection<SysDict> list = sysDictService.listByMap(p);
        model.addAttribute("status", list);
        return PREFIX + "calc_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        SalaryCalc record = ControllerUtils.bindParams(request, SalaryCalc.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");
        IPage<SalaryCalc> page = salaryCalcService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        List<SalaryCalc> records = page.getRecords();
        List<SalaryCalc> list = new ArrayList<SalaryCalc>();
        for (SalaryCalc r : records) {

            SysUser user = sysUserService.getById(r.getEmployeeId());
            r.setUserNo(user.getUserNo());
            r.setUserName(user.getUserNick());
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
    @NeedAuth(value = "salary:calc:start")
    @Req4Model(value = "/start")
    public String add(Model model) {
        return PREFIX + "calc_start.html";
    }

    /**
     * 启动核算，将上月未离职员工列表初始化
     *
     * @param param
     * @return
     */
    @Req4Json(value = "/init", title = "启动核算", parameters = "id")
    public Object init(SalaryCalc param) {
        QueryWrapper queryWrapper = new QueryWrapper();
        SysUser user = new SysUser();
        user.setStatus("1");
        queryWrapper.setEntity(user);
        queryWrapper.ne("user_name", "admin");//排除管理员账号
        //查询未离职员工
        List<SysUser> list = sysUserService.list(queryWrapper);
        String lastMonth = param.getCalcMonth();
        for (SysUser u : list) {
            SalaryCalc p = new SalaryCalc();
            p.setEmployeeId(u.getId());
            p.setCalcMonth(lastMonth);
            QueryWrapper query = new QueryWrapper();
            query.setEntity(p);
            SalaryCalc one = null;
            one = salaryCalcService.getOne(query);
            if (null != one) {
                //如果该员工已启动工资核算则跳过
                continue;
            }
            boolean save = salaryCalcService.save(p);
        }
        return R.success("保存成功");
    }

    /**
     * 修改页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/edit")
    @NeedAuth(value = "salary:calc:edit")
    public String edit(Model model, Integer id) {
        SalaryCalc salaryCalc = getSalaryCalc(id);
        model.addAttribute("salaryCalc", salaryCalc);
        return PREFIX + "calc_edit.html";
    }

    private SalaryCalc getSalaryCalc(Integer id) {
        SalaryCalc salaryCalc = salaryCalcService.getById(id);
        SysUser user = sysUserService.getById(salaryCalc.getEmployeeId());
        salaryCalc.setUserNo(user.getUserNo());
        salaryCalc.setUserName(user.getUserNick());
        return salaryCalc;
    }

    /**
     * 核算
     *
     * @param salaryCalc1
     * @return
     */
    @Req4Json(value = "/calc", title = "核算", parameters = "id")
    public Object calc(SalaryCalc salaryCalc1) {
        SalaryCalc salaryCalc = getSalaryCalc(salaryCalc1.getId());
        Integer status = salaryCalc.getStatus();
        if (status == 2) {
            return R.success("该月份工资已核算");
        }
        SysUser user = sysUserService.getById(salaryCalc.getEmployeeId());
        //1.查询员工信息（类型，等级）
        String userLevel = user.getLevel();
        String userType = user.getType();
        Integer userId = user.getId();


        //2.查询该员工薪资配置项
        UserSalaryConf conf = new UserSalaryConf();
        conf.setEmployeeId(userId);
        QueryWrapper query = new QueryWrapper();
        query.setEntity(conf);
        List<UserSalaryConf> confList = userSalaryConfService.list(query);
        if (Collections.isEmpty(confList)) {
            return R.error("请先配置该员工薪资配置");
        }
        //3.查询该员工出勤
        String calcMonth = salaryCalc.getCalcMonth();
        WorkInfo workInfo = getWorkInfo(userId, calcMonth);
        //4.计算并保存工资记录
        countAmount(confList, calcMonth, workInfo, user);
        //5.更新核算记录状态
        SalaryCalc calc = new SalaryCalc();
        calc.setId(salaryCalc1.getId());
        calc.setStatus(2);
        boolean update = salaryCalcService.updateById(calc);
        if (update) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 获取员工，迟到次数，加班次数，早退次数
     *
     * @param userId
     * @param workMonth
     * @return
     */
    private WorkInfo getWorkInfo(Integer userId, String workMonth) {
        WorkRecord workRecord = new WorkRecord();
        workRecord.setEmployeeId(userId);
        workRecord.setWorkMonth(workMonth);
        QueryWrapper q = new QueryWrapper();
        q.setEntity(workRecord);
        int lateNum = 0;//迟到次数
        int leaveNum = 0;//早退次数
        int overtimeNum = 0;//加班次数
        int preLeaveNum = 0;//请假次数
        List<WorkRecord> list = workRecordService.list(q);
        if (!Collections.isEmpty(list)) {
            for (WorkRecord r : list) {
                if (StringUtils.isNotBlank(r.getUpTime())) {
                    int upTime = Integer.valueOf(r.getUpTime().substring(0, 2));
                    Integer uTime = Integer.valueOf(standardUpTime);
                    if (upTime > uTime || (upTime == uTime && Integer.valueOf(r.getUpTime().substring(3, 5)) > 0)) {
                        //迟到
                        lateNum++;
                    }
                }
                if (StringUtils.isNotBlank(r.getDownTime())) {
                    int downTime = Integer.valueOf(r.getDownTime().substring(0, 2));
                    if (downTime < Integer.valueOf(standardDownTime)) {
                        //早退
                        leaveNum++;
                    }
                    if (downTime >= Integer.valueOf(standardOverTime)) {
                        //加班
                        overtimeNum++;
                    }
                }
                if (5 == r.getWorkStatus()) {
                    preLeaveNum++;
                }
            }
        }
        WorkInfo workInfo = new WorkInfo();
        workInfo.setLateNum(lateNum);
        workInfo.setLeaveNum(leaveNum);
        workInfo.setOvertimeNum(overtimeNum);
        workInfo.setPreLeaveNum(preLeaveNum);
        return workInfo;
    }

    /**
     * 计算工资
     *
     * @param confList
     * @return
     */
    private void countAmount(List<UserSalaryConf> confList, String calcMonth, WorkInfo workInfo, SysUser user) {
        int lateNum = workInfo.getLateNum();
        int leaveNum = workInfo.getLeaveNum();
        int overtimeNum = workInfo.getOvertimeNum();
        int preLeaveNum = workInfo.getPreLeaveNum();

        //1.计算工资扣减项
        Float realitySalary = 0.0f;//实发工资
        Float mustSalary = 0.0f;//应发工资
        Integer userId = 0;
        //3.增加月工资汇总记录
        SalaryRecord r = new SalaryRecord();
        r.setEmployeeId(userId);
        r.setMustSalary(mustSalary);
        r.setRealitySalary(realitySalary);
        r.setSalaryDate(calcMonth);
        salaryRecordService.save(r);
        SalaryDetail tax = new SalaryDetail();//个税

        for (UserSalaryConf c : confList) {
            userId = c.getEmployeeId();
            Integer salaryConfId = c.getSalaryConfId();
            //查询工资配置项
            SalaryConf conf = salaryConfService.getById(salaryConfId);
            float amount = conf.getAmount() * c.getSalaryConfRatio();
            SalaryDetail d = new SalaryDetail();
            d.setCostAmount(amount);
            d.setCostName(conf.getTypeName());
            d.setRecordId(r.getId());
            Integer costNo = conf.getId();
            d.setCostNo(costNo);
            d.setType(conf.getType());
            if (conf.getType().equals("1")) {
                if (costNo == 13) {
                    //加班
                    amount = amount * overtimeNum;
                    d.setCostAmount(amount);
                }
                //工资增加项
                realitySalary += amount;
                mustSalary += amount;
            } else {
                //工资扣减
                if (costNo == 20) {
                    //个税最后计算
                    tax = d;
                    continue;
                } else if (costNo == 21) {
                    //迟到
                    amount = amount * lateNum;
                } else if (costNo == 22) {
                    //请假
                    amount = amount * preLeaveNum;
                } else if (costNo == 25) {
                    //早退
                    amount = amount * leaveNum;
                }
                d.setCostAmount(amount);
                realitySalary -= amount;
            }
            //2.增加工资扣减明细
            salaryDetailService.save(d);
        }
        //计算个税
        Float reality = realitySalary - countTax(realitySalary, tax);
        //3.增加月工资汇总记录
        r.setEmployeeId(userId);
        r.setMustSalary(mustSalary);
        r.setRealitySalary(reality);//实际应发
        r.setSalaryDate(calcMonth);
        salaryRecordService.saveOrUpdate(r);
    }

    /**
     * 计算个税
     *
     * @param realitySalary
     * @param tax
     * @return
     */
    private Float countTax(Float realitySalary, SalaryDetail tax) {
        Float costAmount = 0.0f;
        if (realitySalary > 3000 && realitySalary <= 12000) {
            costAmount = (realitySalary - 210) * 0.1f;
        } else if (realitySalary > 12000 && realitySalary <= 25000) {
            costAmount = (realitySalary - 1410) * 0.2f;
        } else if (realitySalary > 25000 && realitySalary <= 35000) {
            costAmount = (realitySalary - 2660) * 0.25f;
        } else if (realitySalary > 35000 && realitySalary <= 55000) {
            costAmount = (realitySalary - 4410) * 0.30f;
        } else if (realitySalary > 55000 && realitySalary <= 80000) {
            costAmount = (realitySalary - 7160) * 0.35f;
        } else if (realitySalary > 80000) {
            costAmount = (realitySalary - 15160) * 0.45f;
        }
        tax.setCostAmount(costAmount);
        salaryDetailService.save(tax);
        return costAmount;

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Req4Json(value = "/del", title = "删除", parameters = "id")
    public Object del(Integer id) {
        boolean del = salaryCalcService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
