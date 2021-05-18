package com.kll.springcloud.service;

import com.kll.springcloud.model.OrderInfo;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-05-13 12:44
 */
public interface OrderService {
    Boolean decrementProductStore(Integer id, Integer count);

    Boolean increaseProductStore(Integer id, Integer count);

}