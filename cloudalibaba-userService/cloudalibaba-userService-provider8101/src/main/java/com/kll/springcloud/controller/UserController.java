package com.kll.springcloud.controller;

import com.kll.springcloud.entities.MallUser;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.model.UserSignUp;
import com.kll.springcloud.service.UserServiceImpl;
import com.kll.springcloud.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: PaymentController
 * @description:
 * @author: XZQ
 * @create: 2020/3/11 15:26
 **/
@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisService redisService;

    //        redisService.set("payment","nacos",id.toString());
//        redisService.expire("payment", "nacos", 4000, TimeUnit.MILLISECONDS);
    @PostMapping("/login")
    public Object login(@RequestBody UserLogin userLogin) {
        return userService.login(userLogin);
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserSignUp userSignUp) {
        return userService.signUp(userSignUp);
    }

}
