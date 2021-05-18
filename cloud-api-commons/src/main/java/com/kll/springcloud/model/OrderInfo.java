package com.kll.springcloud.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-19 20:20
 */
@Data
public class OrderInfo implements Serializable {
    private String status;

    private String code;

    private String userId;

    private String date;

    private String sealType;

    private List<CartProductAttrInfo> productInfo;
}