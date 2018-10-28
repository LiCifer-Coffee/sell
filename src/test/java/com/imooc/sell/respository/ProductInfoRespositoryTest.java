package com.imooc.sell.respository;

import com.imooc.sell.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRespositoryTest {


    @Autowired
    private ProductInfoRespository respository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("啊啊啊啊啊");
        productInfo.setProductIcon("fadsadsa.jpg");
        productInfo.setProductName("瘦肉粥");
        productInfo.setProductId("123456");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(10);
        ProductInfo productInfo1 = respository.save(productInfo);
        log.info("product:{}",productInfo1);
    }


    @Test
    public void findByProductStatus() {

        List<ProductInfo> productStatus = respository.findByProductStatus(2);
        log.info("products:{}",productStatus);
    }
}