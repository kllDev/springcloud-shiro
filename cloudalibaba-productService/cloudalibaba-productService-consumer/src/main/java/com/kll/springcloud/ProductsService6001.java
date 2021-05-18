package com.kll.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 23:08
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProductsService6001 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication( ProductsService6001.class);
        springApplication.run(args);
    }
}