package com.kll.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 23:03
 */
@SpringBootApplication
@MapperScan("com.kll.springcloud.mapper")
public class UserDuration8101 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(UserDuration8101.class);
        springApplication.run(args);
    }
}