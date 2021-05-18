package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.OrderDetail;
import com.kll.springcloud.entities.ProductDescribeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-29 12:23
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Select("select * from t_order_detail where order_id = #{order_id} ")
    List<OrderDetail> getDetailsByOrder_id(@Param("order_id") String order_id);


}