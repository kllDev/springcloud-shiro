package com.kll.springcloud.model;

import lombok.Data;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-05-14 17:13
 */
@Data
public class SecKillInfo {
    private String userId;
    private Integer count;
    private String packageId;
    private String title;
    private Double price;
}