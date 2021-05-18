package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.Order;
import com.kll.springcloud.entities.ProductDescribeEntity;
import com.kll.springcloud.model.ProductInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
*@program springcloud-shiro
*@description
*@author liangliang.ke
*@date 2021-04-29 12:23
*@version 1.0
*/
public interface OrderMapper extends BaseMapper<Order> {

    @Select("select id from t_order where user_id = #{userId}")
    List<String> selectListByUser(@Param("userId") Integer userId);
}