package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.SalaryConf;
import com.salary.modules.salary.entity.UserSalaryConf;
import com.salary.modules.salary.service.ISalaryConfService;
import com.salary.modules.salary.service.IUserSalaryConfService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
import com.salary.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/salary/user/conf")
public class UserSalaryConfController {

    private static final String PREFIX = "modules/salary/user/conf/";

    @Autowired
    private IUserSalaryConfService userSalaryConfService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISalaryConfService salaryConfService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:user:conf:index")
    public String index(Model model) {
        List<SysUser> users = sysUserService.list();
        model.addAttribute("users", users);
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
        UserSalaryConf record = ControllerUtils.bindParams(request, UserSalaryConf.class);
        //根据工号或姓名查询用户
        String userNo = record.getUserNo();
        String userName = record.getUserName();
        if(StringUtils.isNotBlank(userNo)||StringUtils.isNotBlank(userName)) {
            SysUser user = new SysUser();
            user.setUserNo(userNo);
            user.setUserNick(userName);
            Integer userId = getUserId(user);
            record.setEmployeeId(userId);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");
        IPage<UserSalaryConf> page = userSalaryConfService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        //设置员工姓名和工号
        List<UserSalaryConf> list = setUserInfo(page);
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
     * 设置员工姓名和工号
     *
     * @param page
     * @return
     */
    private List<UserSalaryConf> setUserInfo(IPage<UserSalaryConf> page) {
        List<UserSalaryConf> records = page.getRecords();
        List<UserSalaryConf> list = new ArrayList<UserSalaryConf>();
        for (UserSalaryConf r : records) {
            SysUser user = sysUserService.getById(r.getEmployeeId());
            SalaryConf conf = salaryConfService.getById(r.getSalaryConfId());
            r.setUserNo(user.getUserNo());
            r.setUserName(user.getUserNick());
            r.setSalaryItem(conf.getTypeName());
            list.add(r);
        }
        return list;
    }


    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "salary:user:conf:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        List<SalaryConf> items = salaryConfService.list();
        List<SysUser> users = sysUserService.list();
        model.addAttribute("items", items);
        model.addAttribute("users", users);
        return PREFIX + "conf_add.html";
    }

    /**
     * 保存
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/save", title = "保存", parameters = "id")
    public Object save(UserSalaryConf record) {
        UserSalaryConf r = new UserSalaryConf();
        r.setEmployeeId(record.getEmployeeId());
        r.setSalaryConfId(record.getSalaryConfId());
        QueryWrapper query = new QueryWrapper();
        query.setEntity(r);
        UserSalaryConf one = userSalaryConfService.getOne(query);
        if(null != one){
            return R.error("该员工的该配置项已配置，请勿重复配置");
        }
        boolean save = userSalaryConfService.save(record);
        if (save) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 批量增加
     *
     * @param param
     * @return
     */
    @Req4Json(value = "/batchAdd", title = "批量增加", parameters = "id")
    public Object batchAdd(UserSalaryConf param) {
        QueryWrapper queryWrapper = new QueryWrapper();
        SysUser user = new SysUser();
        user.setStatus("1");
        queryWrapper.setEntity(user);
        queryWrapper.ne("user_name", "admin");//排除管理员账号
        //查询未离职员工
        List<SysUser> list = sysUserService.list(queryWrapper);
        List<SalaryConf> confs = salaryConfService.list();
        for (SysUser u : list) {
            for (SalaryConf conf : confs) {
                UserSalaryConf c = new UserSalaryConf();
                c.setEmployeeId(u.getId());
                c.setSalaryConfId(conf.getId());
                QueryWrapper query = new QueryWrapper();
                query.setEntity(c);
                UserSalaryConf one = userSalaryConfService.getOne(query);
                if (null != one) {
                    continue;
                }
                c.setSalaryConfRatio(1.0f);

                userSalaryConfService.save(c);
            }
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
    @NeedAuth(value = "salary:user:conf:edit")
    public String edit(Model model, Integer id) {
        UserSalaryConf r = userSalaryConfService.getById(id);
        SalaryConf conf = salaryConfService.getById(r.getSalaryConfId());
        r.setSalaryItem(conf.getTypeName());
        model.addAttribute("record", r);
        return PREFIX + "conf_edit.html";
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/update", title = "更新", parameters = "id")
    public Object update(UserSalaryConf record) {
        boolean update = userSalaryConfService.updateById(record);
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
        boolean del = userSalaryConfService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
