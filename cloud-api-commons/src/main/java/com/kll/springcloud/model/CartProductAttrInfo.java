package com.kll.springcloud.model;

import com.kll.springcloud.entities.OrderDetail;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-19 20:22
 */
@Data
public class CartProductAttrInfo implements Serializable {
    private String goods_title;
    private String package_id;
    private String package_title;
    private String package_img;
    private Double package_price;
    private Integer count;

    public static OrderDetail getOrderDetailFromInfo(CartProductAttrInfo info,String order_id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order_id);
        orderDetail.setProductAttrId(Integer.valueOf(info.getPackage_id()));
        orderDetail.setGoodsTitle(info.getGoods_title());
        orderDetail.setPackageTitle(info.getPackage_title());
        orderDetail.setPackagePrice(new BigDecimal(info.getPackage_price().toString()));
        orderDetail.setPackageImg(info.getPackage_img());
        orderDetail.setCount(info.getCount());
        return orderDetail;
    }

    public static CartProductAttrInfo getInfoFromEntity(OrderDetail detail) {
        CartProductAttrInfo info = new CartProductAttrInfo();
        info.setPackage_id(detail.getProductAttrId().toString());
        info.setCount(detail.getCount());
        info.setGoods_title(detail.getGoodsTitle());
        info.setPackage_img(detail.getPackageImg());
        info.setPackage_price(new Double(detail.getPackagePrice().toString()));
        info.setPackage_title(detail.getPackageTitle());
        return info;
    }
}