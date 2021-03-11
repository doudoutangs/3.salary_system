package com.salary.config.beetl.ext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * beetl shiro工具
 *
 * @author sun
 */
public class ShiroExt {

    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public boolean hasAuth(String permission) {
        return getSubject() != null && permission != null && permission.length() > 0 && getSubject().isPermitted(permission);
    }
}
