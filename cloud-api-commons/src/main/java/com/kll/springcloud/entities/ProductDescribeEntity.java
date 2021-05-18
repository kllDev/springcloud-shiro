package com.kll.springcloud.entities;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-04-17 20:02:21
 */
@Data
public class ProductDescribeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer productId;
	/**
	 * 
	 */
	private String img;

}
