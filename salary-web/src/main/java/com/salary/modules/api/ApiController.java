package com.salary.modules.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.salary.contants.UserStatusEnum;
import com.salary.core.R;
import com.salary.modules.sys.user.entity.SysUser;
import com.salary.modules.sys.user.service.ISysUserService;
import com.salary.util.CryptoUtils;
import com.salary.util.JwtTokenUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * api接口
 *
 * @author sun
 */
@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * api鉴权
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    public Object auth(@RequestParam("username") String username, @RequestParam("password") String password) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());
        QueryWrapper query = new QueryWrapper();
        query.eq("user_name", username);
        SysUser sysUser = sysUserService.getOne(query);
        if (sysUser != null) {
            if (!CryptoUtils.verify(password, sysUser.getUserPwd())) {
                return R.error("密码错误");
            }
            if (sysUser.getStatus().equals(UserStatusEnum.UNENABLE.getCode())) {
                return R.error("该账户已被禁用,请联系管理员");
            }
            HashMap<String, Object> result = new HashMap<>();
            result.put("token", JwtTokenUtil.generateToken(String.valueOf(sysUser.getId())));
            return result;
        } else {
            return R.error("账号密码错误");
        }
    }

    /**
     * 测试接口是否走鉴权
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object test() {
        return R.success("登入成功");
    }

}
