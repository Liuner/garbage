package com.own.garbage.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 标题：类名称:GeminiServiceApplication
 * 说明：service启动类
 * 时间：2022/1/28 11:28
 * 作者 @author jingxin
 */
@SpringBootApplication(scanBasePackages = {"com.own.garbage.*"})
@MapperScan(basePackages = "com.own.garbage.dao")
public class GarbageApplication {

    public static void main(String[] args) {
        System.out.println("中文");
        SpringApplication.run(GarbageApplication.class, args);
    }
}
