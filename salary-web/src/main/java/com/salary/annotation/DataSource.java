package com.salary.annotation;


import java.lang.annotation.*;

/**
 * 多数据源切换注解
 *
 * @author sun
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源名称
     * @return
     */
    String db() default "";
}
