package com.salary.modules.sys.menu.controller;


import com.salary.contants.IsMenuEnum;
import com.salary.core.R;
import com.salary.modules.sys.menu.entity.SysMenu;
import com.salary.modules.sys.menu.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-07
 */
@Controller
@RequestMapping("/sys/sysMenu")
public class SysMenuController {

    private static final String PREFIX = "modules/sys/menu/";

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 菜单主页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        return PREFIX + "menu_index.html";
    }

    /**
     * 查询菜单
     *
     * @return
     */
    @RequestMapping(value = "/queryMenus")
    @ResponseBody
    public Object queryMenus() {
        return sysMenuService.list();
    }

    /**
     * 查询ztree数据
     * @return
     */
    @RequestMapping(value = "/queryZtree")
    @ResponseBody
    public Object queryZtree(){
        return sysMenuService.queryZtree();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delMenu")
    @ResponseBody
    public Object delMenu(String ids) {
        boolean del = sysMenuService.delMenu(ids);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }


    /**
     * 添加菜单页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/menu_add")
    public String menuAdd(Model model) {
        model.addAttribute("isMenuEnum", IsMenuEnum.values());
        return PREFIX + "menu_add.html";
    }

    /**
     * 保存菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/menu_save")
    @ResponseBody
    public R menuSave(SysMenu sysMenu) {
        return sysMenuService.menuSave(sysMenu);
    }

    /**
     * 编辑菜单页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/menu_edit")
    public String menuEdit(Model model,Integer id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        model.addAttribute("sysMenu",sysMenu);
        model.addAttribute("isMenuEnum", IsMenuEnum.values());
        return PREFIX + "menu_edit.html";
    }

    /**
     * 更新菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/menu_update")
    @ResponseBody
    public R menuUpdate(SysMenu sysMenu) {
        boolean update = sysMenuService.updateById(sysMenu);
        if (update) {
            return R.success("更新成功");
        }
        return R.error("更新失败");
    }
}
