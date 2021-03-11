package com.salary.modules.sys.dict.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salary.annotation.NeedAuth;
import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;
import com.salary.core.R;
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
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
@Controller
@RequestMapping("/sys/sysDict")
public class SysDictController {

    private static final String PREFIX = "modules/sys/dict/";

    @Autowired
    private ISysDictService sysDictService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "sys:dict:index")
    public String index(Model model) {
        Collection<SysDict> sysDicts = getParentDicts();
        model.addAttribute("roots", sysDicts);

        return PREFIX + "dict_index.html";
    }

    private Collection<SysDict> getParentDicts() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid", "0");
        Collection<SysDict> list = sysDictService.listByMap(map);
        return list;
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        SysDict sysDict = ControllerUtils.bindParams(request, SysDict.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        Integer pid = sysDict.getPid();
        if(null == pid){
            sysDict.setPid(0);
        }
        queryWrapper.setEntity(sysDict);
        queryWrapper.orderByDesc("id");
        IPage<SysDict> page = sysDictService.page(new Page<>(sysDict.getPage(), sysDict.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
     * 查询根节点集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryParent")
    public Object queryParent(HttpServletRequest request) throws Exception {
        return getParentDicts();
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "sys:dict:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        Collection<SysDict> sysDicts = getParentDicts();
        model.addAttribute("roots", sysDicts);
        return PREFIX + "dict_add.html";
    }
    /**
     * 添加字典类型
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "sys:dict:add:type")
    @Req4Model(value = "/add/type")
    public String addType(Model model) {
        return PREFIX + "dict_add_type.html";
    }
    /**
     * 保存
     *
     * @param sysDict
     * @return
     */
    @Req4Json(value = "/save", title = "保存字典表", parameters = "id")
    public Object save(SysDict sysDict) {
        boolean save = sysDictService.save(sysDict);
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
    @NeedAuth(value = "sys:dict:edit")
    public String edit(Model model, Integer id) {
        SysDict sysDict = sysDictService.getById(id);
        model.addAttribute("sysDict", sysDict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 更新
     *
     * @param sysDict
     * @return
     */
    @Req4Json(value = "/update", title = "更新字典表", parameters = "id")
    public Object update(SysDict sysDict) {
        boolean update = sysDictService.updateById(sysDict);
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
        boolean del = sysDictService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
