package com.kll.springcloud.controller;


import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.model.OrderInfo;
import com.kll.springcloud.model.ProductDetailInfo;
import com.kll.springcloud.model.ProductInfo;
import com.kll.springcloud.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

    private final RestTemplate restTemplate;

    private final RedisService redisService;

    @Value("${service-url.product-duration}")
    private String productDurationServerUrl;

    public ProductController(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    @GetMapping("/{id}")
    public ProductDetailInfo getProductInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(productDurationServerUrl + "/product/" + id, ProductDetailInfo.class);
    }


    @GetMapping("/list")
    public ProductInfo[] getProductList() {
        String s = redisService.get("product", "list");
        if (s != null && !s.equals("")) {
            return JSONObject.parseObject(s, ProductInfo[].class);
        } else {
            ProductInfo[] infos = restTemplate
                    .getForObject(productDurationServerUrl + "/product/list", ProductInfo[].class);
            redisService.set("product", "list", JSONObject.toJSONString(infos));
            redisService.expire("product", "list", 360, TimeUnit.SECONDS);
            return infos;
        }
    }

    @GetMapping("/secList")
    public ProductInfo[] getSecKillList() {
        String s = redisService.get("product", "secList");
        if (s != null && !s.equals("")) {
            return JSONObject.parseObject(s, ProductInfo[].class);
        } else {
            ProductInfo[] infos = restTemplate.getForObject(productDurationServerUrl + "/product/secList", ProductInfo[].class);
            redisService.set("product", "secList", JSONObject.toJSONString(infos));
            redisService.expire("product", "secList", 360, TimeUnit.SECONDS);
            return infos;
        }
    }
}
