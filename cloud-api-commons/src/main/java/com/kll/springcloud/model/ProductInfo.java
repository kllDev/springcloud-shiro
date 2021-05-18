package com.kll.springcloud.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-19 09:43
 */
@Data
public class ProductInfo {
    private String id;

    private String title;

    private String img;

    private BigDecimal price;

    private Integer remarksNum;

    private String type;

    private String status;
}