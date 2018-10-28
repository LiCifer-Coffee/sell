package com.imooc.sell.respository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRespositoryTest {

    @Autowired
    private OrderDetailRespository respository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("123456");
        orderDetail.setProductIcon("fdafewfew.jpg");
        orderDetail.setProductId("123456");
        orderDetail.setProductName("大米粥");
        orderDetail.setProductPrice(new BigDecimal(2.0));
        orderDetail.setProductQuantity(10);
        respository.save(orderDetail);
    }

    @Test
    public void findByOrderId() {

        List<OrderDetail> orderDetails = respository.findByOrderId("123456");
        System.out.println(orderDetails);

    }
}