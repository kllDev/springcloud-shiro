package com.kll.springcloud.service;

import com.kll.springcloud.entities.ProductAttrEntity;
import com.kll.springcloud.entities.ProductDescribeEntity;
import com.kll.springcloud.entities.ProductEntity;
import com.kll.springcloud.entities.ProductImgEntity;
import com.kll.springcloud.mapper.ProductAttrMapper;
import com.kll.springcloud.mapper.ProductDescribeMapper;
import com.kll.springcloud.mapper.ProductImgMapper;
import com.kll.springcloud.mapper.ProductMapper;
import com.kll.springcloud.model.ProductInfo;
import com.kll.springcloud.model.ProductAttrInfo;
import com.kll.springcloud.model.ProductDetailInfo;
import com.kll.springcloud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductAttrMapper productAttrMapper;

    @Autowired
    private ProductDescribeMapper productDescribeMapper;

    @Autowired
    private ProductImgMapper productImgMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ProductDetailInfo getProductInfoById(Integer id) {
        ProductEntity product = productMapper.getEntityById(id);
        List<ProductAttrEntity> attrs = productAttrMapper.getEntitiesByProductId(id);
        for (ProductAttrEntity attr : attrs) {
            if(attr.getKillDate()!=null){
                String s = redisService.get("product_store", attr.getId().toString());
                try {
                    attr.setCount(Integer.valueOf(s));
                } catch (NumberFormatException e) {
                    System.out.println(e.getCause().toString());
                }
            }
        }
        List<ProductDescribeEntity> describes = productDescribeMapper.getEntitiesByProductId(id);
        List<ProductImgEntity> imgs = productImgMapper.getEntitiesByProductId(id);
        ProductDetailInfo productDetailInfo = new ProductDetailInfo();
        productDetailInfo.setId(product.getId().toString());
        productDetailInfo.setGoodsImg(imgs.stream().map(ProductImgEntity::getImg).collect(Collectors.toList()));
        productDetailInfo.setTitle(product.getTitle());
        productDetailInfo.setRemarksNum(product.getRemarksnum());
        productDetailInfo.setSetMeal(attrs.stream().map(ProductAttrInfo::getInfoFromEntity).collect(Collectors.toList()));
        productDetailInfo.setGoodsDetail(describes.stream().map(ProductDescribeEntity::getImg).collect(Collectors.toList()));
        return productDetailInfo;
    }

    @Override
    public ProductInfo[] getProductList() {
        List<ProductInfo> productList = productMapper.getProductList();
        ProductInfo[] productInfos = new ProductInfo[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            productInfos[i] = productList.get(i);
        }
        return productInfos;
    }

    @Override
    public ProductInfo[] getSecList() {
        List<ProductInfo> productList = productMapper.getProductList();
        ProductInfo[] productInfos = new ProductInfo[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            productInfos[i] = productList.get(i);
        }
        return productInfos;
    }
}
