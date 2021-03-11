package com.salary.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.salary.contants.UserStatusEnum;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.CryptoUtils;
import com.salary.util.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * shiro用户校验授权类
 *
 * @author sun
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private ISysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        List<String> auths = sysUserService.queryAuth4User(ShiroUtil.getUser().getId());
        Set<String> set = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String auth : auths) {
            set.add(auth);
        }
        info.setStringPermissions(set);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        String password = String.valueOf(token.getPassword());
        QueryWrapper query = new QueryWrapper();;
        query.eq("user_name", userName);
        SysUser sysUser = sysUserService.getOne(query);
        if (sysUser == null) {
            throw new AccountException("该用户不存在");
        }
        if (!CryptoUtils.verify(password, sysUser.getUserPwd())) {
            throw new AccountException("密码错误");
        }
        if (sysUser.getStatus().equals(UserStatusEnum.UNENABLE.getCode())) {
            throw new AccountException("该账户已被禁用,请联系管理员");
        }
        return new SimpleAuthenticationInfo(sysUser, password, getName());
    }
}
