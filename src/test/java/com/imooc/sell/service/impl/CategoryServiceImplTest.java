package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(2);
        log.info("category:{}",productCategory);
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategories = categoryService.findAll();
        log.info("categorys:{}",productCategories);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> type = categoryService.findByCategoryTypeIn(Arrays.asList(2, 3));
        log.info("types:{}",type);
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("三生三世",5);
        ProductCategory category = categoryService.save(productCategory);
        log.info("category:{}", category);

    }
}