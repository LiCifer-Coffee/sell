package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;


    @Test
    public void findByProductStatus() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<ProductInfo> infos = productService.findAll(pageRequest);
        log.info("info:{}================={}============{}",infos.getTotalElements(),infos.getTotalPages(),infos.map(productInfo -> productInfo));
    }

    @Test
    public void save() {
    }
}