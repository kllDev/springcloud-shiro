package com.kll.springcloud.controller;

import com.kll.springcloud.entities.Order;
import com.kll.springcloud.model.OrderInfo;
import com.kll.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-29 12:21
 */
@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Boolean create(@RequestBody OrderInfo orderInfo) {
        return orderService.createOrder(orderInfo);
    }

    @PostMapping("/invalid")
    public Boolean invalid(@RequestBody String orderId) {
        orderService.invalidOrder(orderId);
        return Boolean.TRUE;
    }

    @PostMapping("/pay")
    public Order pay(@RequestBody String code) {
        Order order = orderService.payOrder(code);
        return order;
    }

    @GetMapping("/list/{id}")
    public OrderInfo[] getProductList(@PathVariable("id") Integer id) {
        return orderService.getUserAllOrder(id.toString());
    }

    @PostMapping("/info")
    public OrderInfo info(@RequestBody String orderId) {
        return orderService.getInfo(orderId);
    }
}