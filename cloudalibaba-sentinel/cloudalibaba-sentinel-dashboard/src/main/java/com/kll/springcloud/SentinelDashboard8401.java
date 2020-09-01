package com.kll.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class SentinelDashboard8401 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SentinelDashboard8401.class);
        springApplication.run(args);
    }

}
