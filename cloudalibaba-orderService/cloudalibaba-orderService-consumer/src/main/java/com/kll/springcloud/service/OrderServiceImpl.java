package com.kll.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-05-13 12:45
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisService redisService;


    @Override
    public Boolean decrementProductStore(Integer id, Integer count) {
        String key = "dec_store_lock_" + id;
        RLock lock = redissonClient.getLock(key);
        try {
            //加锁操作很类似Java的ReentrantLock机制
            lock.lock();
            String product_store = redisService.get("product_store", id.toString());
            Integer productCount = Integer.parseInt(product_store);
            if (productCount >= count) {
                redisService.set("product_store", id.toString(), String.valueOf(productCount - count));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
        return true;
    }

    @Override
    public Boolean increaseProductStore(Integer id, Integer count) {
        String key = "dec_store_lock_" + id;
        RLock lock = redissonClient.getLock(key);
        try {
            //加锁操作很类似Java的ReentrantLock机制
            lock.lock();
            String product_store = redisService.get("product_store", id.toString());
            Integer productCount = Integer.parseInt(product_store);
            redisService.set("product_store", id.toString(), String.valueOf(productCount + count));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
        return true;
    }
}