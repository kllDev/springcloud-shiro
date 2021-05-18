package com.kll.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 23:09
 */
@SpringBootApplication
@MapperScan("com.kll.springcloud.mapper")
public class ProductsDuration6101 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ProductsDuration6101.class);
        springApplication.run(args);
    }
}