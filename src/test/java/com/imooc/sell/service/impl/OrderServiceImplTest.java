package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.respository.OrderMasterRespository;
import com.imooc.sell.respository.ProductInfoRespository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {


    @Autowired
    private OrderMasterRespository respository;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductInfoRespository productInfoRespository;

    private final String BUYER_OPENID = "abc123";

    @Test
    public void create() {

        OrderDto orderDto = new OrderDto();

        orderDto.setBuyerAddress("福永街道");
        orderDto.setBuyerName("MrLee");
        orderDto.setBuyerOpenid(BUYER_OPENID);
        orderDto.setBuyerPhone("15920025232");

//        orderDto.setOrderId("12345678");
//        orderDto.setOrderStatus(OrderStatusEnum.NEW.getCode());
//        orderDto.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderDetail orderDetail = new OrderDetail();
//        ProductInfo productInfo = productInfoRespository.findById("123456").orElse(null);

//        orderDetail.setDetailId("12345678");
//        orderDetail.setOrderId(orderDto.getOrderId());
//        orderDetail.setProductName(productInfo.getProductName());
//        orderDetail.setProductId(productInfo.getProductId());
//        orderDetail.setProductIcon(productInfo.getProductIcon());
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductId("123456");


        orderDetail.setProductQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

//        BigDecimal amount = new BigDecimal(BigInteger.ZERO);
//        for (OrderDetail od :orderDetails) {
//            amount = od.getProductPrice().add(amount);
//        }
//
//        orderDto.setOrderAmount(amount);
        orderDto.setOrderDetailList(orderDetails);

        OrderDto orderDto1 = orderService.create(orderDto);

    }

    @Test
    public void findOne() {

        OrderDto orderDto = orderService.findOne("1540915116215953602");
        log.info("orderDto:{}",orderDto);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDto> list = orderService.findList(BUYER_OPENID, request);
        log.info("list:{}",list);

    }

    @Test
    public void cancel() {

        OrderDto orderDto = orderService.findOne("1540915116215953602");
        orderDto = orderService.cancel(orderDto);

    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}