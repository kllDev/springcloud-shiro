package com.kll.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-28 17:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order_detail")
public class OrderDetail implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String orderId;

    private String goodsTitle;

    private String packageTitle;

    private BigDecimal packagePrice;

    private String packageImg;

    private Integer productAttrId;

    private Integer count;
}