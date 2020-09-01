package com.kll.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.entities.CommonResult;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.service.impl.RedisService;
import com.kll.springcloud.service.impl.UserLoginServiceImpl;
import com.kll.springcloud.util.JwtUtil;
import com.kll.springcloud.util.PasswordUtil;
import com.kll.springcloud.util.Result;
import com.kll.springcloud.util.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: PaymentController
 * @description:
 * @author: XZQ
 * @create: 2020/3/11 15:26
 **/
@RestController
@Slf4j
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserLoginServiceImpl userLoginService;
    @Autowired
    private RedisService redisService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/user/login/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        redisService.set("payment","nacos",id.toString());
        redisService.expire("payment", "nacos", 4000, TimeUnit.MILLISECONDS);
        return "nacos register, serverport=" + serverPort + "\t id:" + id;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<JSONObject> login(@RequestBody UserLogin loginUser) throws Exception {
        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        String username = loginUser.getUserName();
        String password = loginUser.getPassWord();

        //1. 校验用户是否有效
        UserLogin sysUser = userLoginService.getUserByAccount(username);
        result =userLoginService.checkUserIsEffective(sysUser);
        if (result.getCode()==500) {
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String syspassword = sysUser.getPassWord();
        if (!syspassword.equals(userpassword)) {
            result.setCode(500);
            result.setMessage("用户名或密码错误");
            return result;
        }

        //用户登录信息
        userInfo(sysUser, result);

        return result;
    }

    /**
     * 用户信息
     *
     * @param userLogin
     * @param result
     * @return
     */
    private CommonResult<JSONObject> userInfo(UserLogin userLogin, CommonResult<JSONObject> result) {
        String syspassword = userLogin.getPassWord();
        String username = userLogin.getUserName();
        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        redisService.set(CommonConstant.PREFIX_USER_TOKEN, username, token);
        // 设置超时时间
        redisService.expire(CommonConstant.PREFIX_USER_TOKEN ,username, JwtUtil.EXPIRE_TIME / 1000,TimeUnit.SECONDS);
        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        obj.put("token", token);
        obj.put("userInfo", userLogin);
        result.setData(obj);
        result.setMessage("登录成功");
        return result;
    }
}
