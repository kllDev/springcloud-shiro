package com.kll.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 23:24
 */
@SpringBootApplication
public class RabbitMQMsgSender4101 {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(RabbitMQMsgSender4101.class);
        springApplication.run(args);
    }
}