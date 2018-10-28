package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.respository.ProductInfoRespository;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRespository respository;

    @Override
    public List<ProductInfo> findByProductStatus(Integer status) {
        return respository.findByProductStatus(status);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return respository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return respository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return respository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return respository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDto> cartDtos) {
        respository.increaseStock(cartDtos);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtos) {

        for (CartDto cartDto:cartDtos) {
            ProductInfo info = respository.findById(cartDto.getProductId()).orElse(null);
            if(info == null){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = info.getProductStock() - cartDto.getProductQuantity();
            if (result < 0) {
                new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            info.setProductStock(result);
            respository.save(info);
        }

    }
}
