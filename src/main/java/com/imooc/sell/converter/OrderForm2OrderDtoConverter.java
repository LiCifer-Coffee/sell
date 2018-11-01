package com.imooc.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDtoConverter {

    public static OrderDto converter(OrderForm orderForm) {

        Gson gson = new Gson();

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setOrderId(orderForm.getOpenid());
        orderDto.setBuyerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());

        }catch (Exception e){
            log.error("【对象转换错误】string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDto.setOrderDetailList(orderDetails);
        return orderDto;

    }


}
