package com.salary.aspect;

import com.salary.annotation.NeedAuth;
import com.salary.contants.ExceptionEnum;
import com.salary.exception.NoAuthException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 权限校验拦截器
 *
 * @author sun
 */
@Aspect
@Component
public class NeedAuthAspect {

    @Pointcut("@annotation(com.salary.annotation.NeedAuth)")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        NeedAuth needAuth = method.getAnnotation(NeedAuth.class);
        String value = needAuth.value();
        if (StringUtils.isNotBlank(value)) {
            Subject subject = SecurityUtils.getSubject();
            boolean flag = subject.isPermitted(value);
            if (flag == false) {
                throw new NoAuthException(ExceptionEnum.NO_AUTH);
            }
        }
    }
}
