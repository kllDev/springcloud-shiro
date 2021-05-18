package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.ProductAttrEntity;
import com.kll.springcloud.entities.ProductImgEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-17 22:57
 */
public interface ProductImgMapper extends BaseMapper<ProductImgEntity> {

    @Select("select * from product_img where product_id = #{product_id} ")
    List<ProductImgEntity> getEntitiesByProductId(@Param("product_id") Integer product_id);

}