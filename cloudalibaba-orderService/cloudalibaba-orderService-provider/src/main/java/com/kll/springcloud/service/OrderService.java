package com.kll.springcloud.service;

import com.kll.springcloud.entities.Order;
import com.kll.springcloud.model.OrderInfo;


/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-29 12:24
 */
public interface OrderService {

    Boolean createOrder(OrderInfo orderInfo);

    void invalidOrder(String orderId);

    Order payOrder(String orderId);

    OrderInfo getInfo(String orderId);

    OrderInfo[] getUserAllOrder(String userId);

    Boolean increaseProductStore(Integer id, Integer count);

}