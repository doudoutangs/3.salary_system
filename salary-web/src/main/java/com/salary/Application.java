package com.salary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.salary")
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("薪资管理系统启动成功啦!!!");
    }

}
