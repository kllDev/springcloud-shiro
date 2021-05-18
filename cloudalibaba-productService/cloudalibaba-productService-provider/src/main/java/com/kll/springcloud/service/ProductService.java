package com.kll.springcloud.service;

import com.kll.springcloud.model.ProductDetailInfo;
import com.kll.springcloud.model.ProductInfo;

public interface ProductService {

    ProductDetailInfo getProductInfoById(Integer id);

    ProductInfo[] getProductList();

    ProductInfo[] getSecList();

}
