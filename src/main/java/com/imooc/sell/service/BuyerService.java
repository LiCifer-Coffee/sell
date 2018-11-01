package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;

public interface BuyerService {


    OrderDto findOrderOne(String openid, String orderId);

    OrderDto cancelOrder(String openid,String orderId);


}
