package com.kll.springcloud.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-04-17 20:02:21
 */
@Data
@TableName("product_attr")
public class ProductAttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer productId;

	private String intro;

	private BigDecimal price;

	private String img;

	private Integer count;

	private Integer killCount;

	private Date killDate;

}
