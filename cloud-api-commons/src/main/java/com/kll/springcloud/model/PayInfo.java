package com.kll.springcloud.model;

import lombok.Data;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-05-12 16:20
 */
@Data
public class PayInfo {
    private String orderId;

    private Double price;

    private String title;
}