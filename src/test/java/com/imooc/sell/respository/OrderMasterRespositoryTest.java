package com.imooc.sell.respository;

import com.imooc.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRespositoryTest {

    @Autowired
    private OrderMasterRespository respository;

    @Test
    public void findByBuyerOpenid() {
        PageRequest page = new PageRequest(0,1);
        Page<OrderMaster> pages = respository.findByBuyerOpenid("123abc", page);
        System.out.println(pages.getTotalElements());
    }

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("MrLee");
        orderMaster.setBuyerName("MrLee");
        orderMaster.setBuyerOpenid("123abc");
        orderMaster.setBuyerPhone("18999998877");
        orderMaster.setOrderId("1234567");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        respository.save(orderMaster);
    }
}