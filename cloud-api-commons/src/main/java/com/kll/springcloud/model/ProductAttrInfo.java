package com.kll.springcloud.model;

import com.kll.springcloud.entities.ProductAttrEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-17 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttrInfo {
    private String id;

    private String img;

    private String intro;

    private Double price;

    private Integer count;

    private Date killDate;

    public static ProductAttrInfo getInfoFromEntity(ProductAttrEntity productAttrEntity) {
        ProductAttrInfo productAttrInfo = new ProductAttrInfo();
        productAttrInfo.setId(String.valueOf(productAttrEntity.getId()));
        productAttrInfo.setImg(productAttrEntity.getImg());
        productAttrInfo.setIntro(productAttrEntity.getIntro());
        productAttrInfo.setPrice(productAttrEntity.getPrice().doubleValue());
        productAttrInfo.setCount(productAttrEntity.getCount());
        productAttrInfo.setKillDate(productAttrEntity.getKillDate());
        return productAttrInfo;
    }
}