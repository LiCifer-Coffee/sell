package com.imooc.sell.respository;

import com.imooc.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRespositoryTest {

    @Autowired
    private ProductCategoryRespository respository;


    @Test
    public void findOneTest(){
        ProductCategory productCategory = respository.findById(1).orElse(null);
        log.info("productCategory:{}",productCategory);
    }

    @Test
    @Transactional
    public void insertTest(){
        ProductCategory productCategory = new ProductCategory("男生最爱",4);
        ProductCategory productCategory1 = respository.save(productCategory);
        log.info("==================P1:{}",productCategory1);
    }


    @Test
    public void testFindList(){

        List<ProductCategory> productCategories = respository.findByCategoryTypeIn(Arrays.asList(2,3));
        Assert.assertNotEquals(0,productCategories.size());

    }


}