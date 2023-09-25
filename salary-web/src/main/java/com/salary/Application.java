package com.salary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: QQ:553039957
 * @Date: 2023/9/25 15:40
 * @Description:
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 3. gitee(码云)主页：https://gitee.com/spdoudoutang
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.salary")
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("薪资管理系统启动成功啦!!!");
        System.out.println(" * 0. QQ:553039957\n" +
                " * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）\n" +
                " * 2. github主页：https://github.com/doudoutangs\n" +
                " * 3. gitee(码云)主页：https://gitee.com/spdoudoutang");
    }

}
