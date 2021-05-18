package com.kll.springcloud.service.impl;

import com.kll.springcloud.entities.Order;
import com.kll.springcloud.entities.OrderDetail;
import com.kll.springcloud.entities.ProductAttrEntity;
import com.kll.springcloud.mapper.OrderDetailMapper;
import com.kll.springcloud.mapper.OrderMapper;
import com.kll.springcloud.mapper.ProductAttrMapper;
import com.kll.springcloud.model.CartProductAttrInfo;
import com.kll.springcloud.model.OrderInfo;
import com.kll.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-29 12:24
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductAttrMapper productAttrMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public Boolean createOrder(OrderInfo orderInfo) {
        Order order = new Order();
        if (orderInfo.getCode() == null) {
            UUID uuid = UUID.randomUUID();
            order.setId(uuid.toString().replaceAll("-", ""));
        } else {
            order.setId(orderInfo.getCode());
        }
        order.setStatus("未支付");
        order.setUserId(Integer.valueOf(orderInfo.getUserId()));
        order.setDate(new Date());
        String orderKey = "order_create_lock";
        RLock orderLock = redissonClient.getLock(orderKey);
        try {
            //加锁操作很类似Java的ReentrantLock机制
            orderLock.lock();
            orderMapper.insert(order);
            for (CartProductAttrInfo cartProductAttrInfo : orderInfo.getProductInfo()) {
                RLock lock = redissonClient.getLock("store_lock_" + cartProductAttrInfo.getPackage_id());
                OrderDetail detail = CartProductAttrInfo
                        .getOrderDetailFromInfo(cartProductAttrInfo, order.getId());
                try {
                    //加锁操作很类似Java的ReentrantLock机制
                    lock.lock();
                    ProductAttrEntity productAttrEntity = productAttrMapper.selectById(cartProductAttrInfo.getPackage_id());
                    if (productAttrEntity.getCount() < cartProductAttrInfo.getCount()) {
                        return Boolean.FALSE;
                    }
                    productAttrEntity.setCount(productAttrEntity.getCount() - cartProductAttrInfo.getCount());
                    productAttrMapper.updateById(productAttrEntity);
                    orderDetailMapper.insert(detail);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    //解锁
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            orderLock.unlock();
        }
        return Boolean.TRUE;
    }

    @Override
    public void invalidOrder(String code) {
        Order order = orderMapper.selectById(code.replaceAll("\"", ""));
        List<OrderDetail> detailsByOrder_id = orderDetailMapper.getDetailsByOrder_id(order.getId());
        //这把锁防止了消息的重复消费， 短时间内的幂等性
        boolean orderLocked = redisService.setnx("order_invalid", code, "invalid", 30, TimeUnit.SECONDS);
        if (orderLocked) {
            for (OrderDetail orderDetail : detailsByOrder_id) {
                String key = "store_lock_" + orderDetail.getProductAttrId();
                RLock lock = redissonClient.getLock(key);
                try {
                    lock.lock();
                    ProductAttrEntity productAttrEntity = productAttrMapper.selectById(orderDetail.getProductAttrId());
                    productAttrEntity.setCount(productAttrEntity.getCount() + orderDetail.getCount());
                    productAttrMapper.updateById(productAttrEntity);
                    increaseProductStore(orderDetail.getProductAttrId(), orderDetail.getCount());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //解锁
                    lock.unlock();
                }
            }
            if (order.getStatus().equals("未支付")) {
                String key = "order_lock_" + order.getId();
                RLock lock = redissonClient.getLock(key);
                try {
                    //加锁操作很类似Java的ReentrantLock机制
                    lock.lock();
                    order.setStatus("超时未支付");
                    orderMapper.updateById(order);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //解锁
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public Order payOrder(String code) {
        Order order = orderMapper.selectById(code.replaceAll("\"", ""));
        if (order.getStatus().equals("未支付")) {
            order.setStatus("订单已支付");
            orderMapper.updateById(order);
        }
        return order;
    }

    @Override
    public OrderInfo getInfo(String orderId) {
        Order order = orderMapper.selectById(orderId.replaceAll("\"", ""));
        List<OrderDetail> detailsByOrder_id = orderDetailMapper.getDetailsByOrder_id(orderId);
        List<CartProductAttrInfo> collect = detailsByOrder_id.stream().map(CartProductAttrInfo::getInfoFromEntity)
                .collect(Collectors.toList());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCode(order.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderInfo.setDate(formatter.format(order.getDate()));
        orderInfo.setUserId(order.getUserId().toString());
        orderInfo.setStatus(order.getStatus());
        orderInfo.setStatus(order.getStatus());
        orderInfo.setProductInfo(collect);
        return orderInfo;
    }

    @Override
    public OrderInfo[] getUserAllOrder(String userId) {
        List<String> codes = orderMapper.selectListByUser(Integer.valueOf(userId));
        List<OrderInfo> infos = new ArrayList<>();
        for (String code : codes) {
            Order order = orderMapper.selectById(code);
            List<OrderDetail> detailsByOrder_id = orderDetailMapper.getDetailsByOrder_id(code);
            List<CartProductAttrInfo> collect = detailsByOrder_id.stream().map(CartProductAttrInfo::getInfoFromEntity)
                    .collect(Collectors.toList());
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setCode(order.getId());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderInfo.setDate(formatter.format(order.getDate()));
            orderInfo.setUserId(order.getUserId().toString());
            orderInfo.setStatus(order.getStatus());
            orderInfo.setProductInfo(collect);
            infos.add(orderInfo);
        }
        infos.sort(Comparator.comparing(OrderInfo::getDate));
        OrderInfo[] list = new OrderInfo[infos.size()];
        int i = 0;
        for (OrderInfo info : infos) {
            list[i] = info;
            i++;
        }
        return list;
    }

    @Override
    public Boolean increaseProductStore(Integer id, Integer count) {
        String key = "store_lock_" + id;
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