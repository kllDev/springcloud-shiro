package com.kll.springcloud.controller;

import com.kll.springcloud.model.ProductDetailInfo;
import com.kll.springcloud.model.ProductInfo;
import com.kll.springcloud.service.RedisService;
import com.kll.springcloud.service.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ProductController
 * @description:
 * @author: XZQ
 * @create: 2020/3/11 15:26
 **/
@RestController()
@Slf4j
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private RedisService redisService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/{id}")
    public ProductDetailInfo getProductInfo(@PathVariable("id") Integer id) {
        return productService.getProductInfoById(id);
    }

    @GetMapping("/list")
    public ProductInfo[] getProductList(){
        return productService.getProductList();
    }

    @GetMapping("/secList")
    public ProductInfo[] secList(){
        return productService.getSecList();
    }
}
