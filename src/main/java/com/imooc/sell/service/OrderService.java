package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    OrderDto create(OrderDto orderDto);

    OrderDto findOne(String orderId);

    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    OrderDto cancel(OrderDto orderDto);

    OrderDto finish(OrderDto orderDto);

    OrderDto paid(OrderDto orderDto);


}
