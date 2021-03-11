package com.salary.modules.salary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
import com.salary.modules.salary.entity.Notice;
import com.salary.modules.salary.service.INoticeService;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.ControllerUtils;
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
@RequestMapping("/salary/notice")
public class NoticeController {

    private static final String PREFIX = "modules/salary/notice/";

    @Autowired
    private INoticeService noticeService;
    @Autowired
    private ISysUserService sysUserService;
    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "salary:notice:index")
    public String index(Model model) {
        return PREFIX + "notice_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        Notice record = ControllerUtils.bindParams(request, Notice.class);
        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.setEntity(record);
        queryWrapper.orderByDesc("id");
        queryWrapper.like(StringUtils.isNotBlank(record.getTitle()),"title",record.getTitle());
        IPage<Notice> page = noticeService.page(new Page<>(record.getPage(), record.getLimit()), queryWrapper);
        List<Notice> records = page.getRecords();
        List<Notice> list = new ArrayList<Notice>();
        for (Notice r : records) {
            SysUser user = sysUserService.getById(r.getReleaseId());
            r.setReleaseName(user.getUserNick());
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
    @NeedAuth(value = "salary:notice:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        return PREFIX + "notice_add.html";
    }

    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Req4Json(value = "/save", title = "保存", parameters = "id")
    public Object save(Notice param) {
        Integer id = ShiroUtil.getUser().getId();
        param.setReleaseId(id);
        boolean save = noticeService.save(param);
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
    @NeedAuth(value = "salary:notice:edit")
    public String edit(Model model, Integer id) {
        Notice params = noticeService.getById(id);
        model.addAttribute("params", params);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Req4Json(value = "/update", title = "更新", parameters = "id")
    public Object update(Notice record) {
        boolean update = noticeService.updateById(record);
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
        boolean del = noticeService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
