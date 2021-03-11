package com.salary.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

/**
 * 继承requestmapping增加日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface Req4Model {

    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default "";

    @AliasFor(annotation = RequestMapping.class)
    String[] path() default "";

    /**
     * 日志名称
     *
     * @return
     */
    String title() default "";

    /**
     * 需要记录的参数
     *
     * @return
     */
    String parameters() default "";


}
