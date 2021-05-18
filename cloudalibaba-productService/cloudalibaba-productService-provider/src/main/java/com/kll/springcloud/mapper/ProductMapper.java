package com.kll.springcloud.mapper;

import com.kll.springcloud.entities.ProductEntity;
import com.kll.springcloud.model.ProductInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ProductMapper{

    @Select("select * from product where id = #{id} ")
    ProductEntity getEntityById(@Param("id") Integer id);

    @Select("select product.id,product.title,product.type,product.remarksNum,product_img.img,product_attr.price from product , product_img ,product_attr WHERE product_img.product_id=product.id AND product_attr.product_id=product.id AND product_attr.id IN (SELECT min(id) FROM product_attr GROUP BY product_id) AND product_img.id IN (SELECT min(id) FROM product_img GROUP BY product_id)")
    List<ProductInfo> getProductList();

}
