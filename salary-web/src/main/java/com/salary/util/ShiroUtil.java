package com.salary.util;

import com.salary.modules.sys.user.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 *
 * @author sun
 */
public class ShiroUtil {


    /**
     * 获取用户
     *
     * @return
     */
    public static SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取subject
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 设置session值
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        SecurityUtils.getSubject().getSession().setAttribute(key, value);
    }

    /**
     * 获取session值
     *
     * @param key
     */
    public static void get(String key) {
        SecurityUtils.getSubject().getSession().getAttribute(key);
    }
}
