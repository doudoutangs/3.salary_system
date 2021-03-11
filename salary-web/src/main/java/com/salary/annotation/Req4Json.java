package com.salary.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * 继承requestmapping response 增加日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
@ResponseBody
public @interface Req4Json {

    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default "";

    @AliasFor(annotation = RequestMapping.class)
    String[] path() default "";

    /**
     * 日志名称
     * @return
     */
    String title() default "";

    /**
     * 需要记录的参数
     * @return
     */
    String parameters() default "";


}
