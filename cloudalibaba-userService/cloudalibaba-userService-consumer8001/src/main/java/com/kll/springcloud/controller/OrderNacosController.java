package com.kll.springcloud.controller;


import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.entities.CommonResult;
import com.kll.springcloud.entities.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin("*")
public class OrderNacosController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @GetMapping("/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serverUrl + "/payment/nacos/" + id, String.class);
    }

    @RequestMapping(value = "/nacos/login", method = RequestMethod.POST)
    public CommonResult<JSONObject> login(@RequestBody UserLogin loginUser) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);
        return restTemplate.postForObject(serverUrl + "/login", loginUser, CommonResult.class);
    }
}
