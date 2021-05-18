package com.kll.springcloud.controller;

import com.kll.springcloud.entities.Order;
import com.kll.springcloud.model.OrderInfo;
import com.kll.springcloud.model.ResultModel;
import com.kll.springcloud.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static com.kll.springcloud.config.DirectRabbitConfig.DELAY_EXCHANGE_NAME;
import static com.kll.springcloud.config.DirectRabbitConfig.DELAY_QUEUEA_ROUTING_KEY;


/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-29 10:40
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    private final OrderService orderService;

    @Value("${service-url.rabbitMQ-msg-sender}")
    private String mqServiceUrl;


    @Value("${service-url.order-duration}")
    private String orderDurationServiceUrl;

    public OrderController(RabbitTemplate rabbitTemplate,
                           RestTemplate restTemplate,
                           OrderService orderService) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public String create(@RequestBody OrderInfo orderInfo) {
        String orderId = restTemplate
                .postForObject(orderDurationServiceUrl + "/order/create", orderInfo, String.class);
        restTemplate.postForObject(mqServiceUrl + "/amqp/orderDelayMsg", orderId, String.class);
        if (orderId != null) {
            return orderId;
        } else {
            return "0";
        }
    }

    @PostMapping("/pay/done/{code}")
    public void pay(@PathVariable String code) {
        Order order = restTemplate.postForObject(orderDurationServiceUrl + "/order/pay", code, Order.class);
        restTemplate.postForObject(mqServiceUrl + "/amqp/socket/"+order.getUserId(), "订单:"+order.getId()+"已完成支付", String.class);
    }


    @GetMapping("/list/{id}")
    public OrderInfo[] getProductList(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(orderDurationServiceUrl + "/order/list/" + id, OrderInfo[].class);
    }

    @PostMapping("/secKill")
    public ResultModel secKil(@RequestBody OrderInfo orderInfo) {
        Boolean res=
                orderService.decrementProductStore(Integer.valueOf(orderInfo.getProductInfo().get(0).getPackage_id()),
                orderInfo.getProductInfo().get(0).getCount());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (res) {
            orderInfo.setCode(uuid);
            rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, orderInfo);
//            restTemplate.postForObject(mqServiceUrl+"/amqp/secKill", orderInfo, String.class);
            return ResultModel.success("商品秒杀成功",uuid);
        }else{
            return ResultModel.fail("秒杀活动已结束");
        }
    }
}