package com.kll.springcloud.listener;

import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.model.OrderInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

import static com.kll.springcloud.config.DirectRabbitConfig.DEAD_LETTER_ORDER_QUEUE_NAME;
import static com.kll.springcloud.var.DeadLetterVar.DEAD_LETTER_QUEUEA_NAME;
import static com.kll.springcloud.var.DeadLetterVar.DEAD_LETTER_QUEUEB_NAME;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 15:45
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    private final RestTemplate restTemplate;

    @Value("${service-url.order-duration}")
    private String orderDurationServiceUrl;

    @Value("${service-url.rabbitMQ-msg-sender}")
    private String mqServiceUrl;

    public DeadLetterQueueConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) {
        OrderInfo orderInfo = JSONObject.parseObject(new String(message.getBody()), OrderInfo.class);
        Boolean res = restTemplate.postForObject(orderDurationServiceUrl + "/order/create", orderInfo, Boolean.class);
        restTemplate.postForObject(mqServiceUrl + "/amqp/orderDelayMsg", orderInfo.getCode(), String.class);
        try {
            if (res) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列B收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DEAD_LETTER_ORDER_QUEUE_NAME)
    public void orderDelay(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列A收到消息：{}", new Date().toString(), msg);
        Boolean res = restTemplate
                .postForObject(orderDurationServiceUrl + "/order/invalid", msg, Boolean.class);
        if(res){
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}