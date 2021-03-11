package com.salary.aspect;

import com.salary.annotation.Req4Json;
import com.salary.annotation.Req4Model;

import com.salary.modules.sys.log.entity.SysBizLog;
import com.salary.modules.sys.log.service.ISysBizLogService;
import com.salary.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

/**
 * 自定义req注解拦截器 记录日志
 *
 * @author sun
 */
@Aspect
@Component
public class ReqAspect {

    @Autowired
    private ISysBizLogService sysBizLogService;

    @Pointcut("@annotation(com.salary.annotation.Req4Model)||@annotation(com.salary.annotation.Req4Json)")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        Req4Model req4Model = method.getAnnotation(Req4Model.class);
        Req4Json req4Json = method.getAnnotation(Req4Json.class);
        SysBizLog sysBizLog = new SysBizLog();
        sysBizLog.setMethod(method.getName());
        sysBizLog.setClassName(clazzName);
        if (req4Model != null) {
            modelHandle(req4Model, sysBizLog, request);
        }
        if (req4Json != null) {
            jsonHandle(req4Json, sysBizLog, request);
        }
    }

    private void modelHandle(Req4Model req4Model, SysBizLog sysBizLog, HttpServletRequest request) throws ParseException {
        String modelTitle = req4Model.title();
        String modelParams = req4Model.parameters();
        if (StringUtils.isNotBlank(modelTitle)) {
            sysBizLog.setTitle(modelTitle);
            sysBizLog.setCreateTime(DateUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            StringBuilder sb = new StringBuilder("{");
            if (StringUtils.isNotBlank(modelParams)) {
                String[] ps = modelParams.split(",");
                for (String s : ps) {
                    sb.append(s + ":" + request.getParameter(s) + ",");
                }
                sb.append("}");
            }
            sysBizLog.setParams(sb.toString());
            sysBizLogService.save(sysBizLog);
        }
    }

    private void jsonHandle(Req4Json req4Json, SysBizLog sysBizLog, HttpServletRequest request) throws ParseException {
        String jsonTitle = req4Json.title();
        String jsonParams = req4Json.parameters();
        if (StringUtils.isNotBlank(jsonTitle)) {
            sysBizLog.setTitle(jsonTitle);
            sysBizLog.setCreateTime(DateUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            StringBuilder sb = new StringBuilder("{");
            if (StringUtils.isNotBlank(jsonParams)) {
                String[] ps = jsonParams.split(",");
                for (String s : ps) {
                    sb.append(s + ":" + request.getParameter(s) + ",");
                }
                sb.append("}");
            }
            sysBizLog.setParams(sb.toString());
            sysBizLogService.save(sysBizLog);
        }
    }
}
