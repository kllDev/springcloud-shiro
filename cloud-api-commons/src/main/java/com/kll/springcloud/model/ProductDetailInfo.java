package com.kll.springcloud.model;

import lombok.Data;

import java.util.List;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description 商品详情模型
 * @date 2021-04-17 15:06
 */
@Data
public class ProductDetailInfo {
    private String id;

    private List<String> goodsImg;

    private String title;

    private Integer remarksNum;

    private List<ProductAttrInfo> setMeal;

    private List<String> goodsDetail;
}