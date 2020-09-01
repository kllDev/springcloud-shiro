package com.kll.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kll.springcloud.mapper")
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MyApplication.class);
        springApplication.run(args);
    }
}
