package com.kll.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 22:55
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderService5001 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication( OrderService5001.class);
        springApplication.run(args);
    }
}