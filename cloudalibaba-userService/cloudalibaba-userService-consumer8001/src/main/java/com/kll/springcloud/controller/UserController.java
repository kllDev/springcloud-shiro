package com.kll.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.kll.springcloud.entities.MallUser;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.model.UserSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-18 10:47
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-duration}")
    private String userDurationServerUrl;

    @PostMapping("/login")
    public MallUser login(@RequestBody UserLogin userLogin) {
        return restTemplate.postForObject(userDurationServerUrl + "/user/login", userLogin, MallUser.class);
    }


    @PostMapping("/signUp")
    public String signUp(@RequestBody UserSignUp userSignUp) {
        return restTemplate.postForObject(userDurationServerUrl + "/user/signUp", userSignUp, String.class);
    }
}