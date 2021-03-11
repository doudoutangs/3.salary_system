package com.salary.modules.sys.index.controller;

import com.salary.core.R;
import com.salary.modules.sys.menu.entity.SysMenu;
import com.salary.modules.sys.menu.service.ISysMenuService;
import com.salary.util.ShiroUtil;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统主页控制层
 *
 * @author sun
 */
@Controller
public class SysIndexController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Value("${salary.app-name}")
    private String appName;

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 系统主页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<SysMenu> sysMenuList = sysMenuService.queryIndexMenus(ShiroUtil.getUser().getId());
        model.addAttribute("sysUser", ShiroUtil.getUser());
        model.addAttribute("sysMenuList", sysMenuList);
        model.addAttribute("appName", appName);
        return "index.html";
    }

    /**
     * 登入页
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("appName", appName);
        return "login.html";
    }

    /**
     * 登入方法
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public Object doLogin(HttpServletRequest request) {
        String userName = request.getParameter("userName").trim();
        String userPwd = request.getParameter("userPwd").trim();
        String rememberMe = request.getParameter("rememberMe");
        Subject currentUser = ShiroUtil.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPwd.toCharArray());
        if ("on".equals(rememberMe)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }
        try {
            currentUser.login(token);
        } catch (AccountException e) {
            return R.error(e.getMessage());
        }

        return R.success("登入成功");
    }


    /**
     * 登出
     */
    @RequestMapping(value = "/logout")
    public void logout() {
        ShiroUtil.getSubject().logout();
    }

    /**
     * welcome
     *
     * @return
     */
    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "welcome.html";
    }

    /**
     * 未授权页面
     *
     * @return
     */
    @RequestMapping(value = "/noAuth")
    public String noAuth() {
        return "noAuth.html";
    }

    @RequestMapping(ERROR_PATH)
    public String error() {
        return "/404.html";
    }

    @Override
    public String getErrorPath() {
        return null;
    }


}
