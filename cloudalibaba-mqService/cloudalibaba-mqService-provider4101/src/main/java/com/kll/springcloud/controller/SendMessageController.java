package com.kll.springcloud.controller;

import com.kll.springcloud.model.OrderInfo;
import com.kll.springcloud.server.WebSocketServer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.kll.springcloud.config.DirectRabbitConfig.*;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-19 18:51
 */
@RestController
@RequestMapping("/amqp")
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @PostMapping("/orderDelayMsg")
    public String orderDelayMsg(@RequestBody String orderId) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_ORDER_QUEUE_ROUTING_KEY, orderId);
        return "ok";
    }

    @PostMapping("/sendMsg")
    public String sendDirectMessage(@RequestBody OrderInfo map) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEB_ROUTING_KEY, map);
        return "ok";
    }

    @PostMapping("/secKill")
    public String secKill(@RequestBody OrderInfo map) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, map);
        return "ok";
    }

    @PostMapping("/socket/{userId}")
    public void socket(@RequestBody String msg, @PathVariable String userId) {
        WebSocketServer.sendInfo(msg,userId);
    }

}