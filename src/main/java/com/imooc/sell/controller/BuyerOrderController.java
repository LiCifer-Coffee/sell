package com.imooc.sell.controller;


import com.imooc.sell.VO.ResultVo;
import com.imooc.sell.converter.OrderForm2OrderDtoConverter;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.util.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderForm2OrderDtoConverter.converter(orderForm);

        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.info("购物车为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDto orderDto1 = orderService.create(orderDto);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDto1.getOrderId());
        return ResultVoUtil.success(map);
    }


    @GetMapping("list")
    public ResultVo<List<OrderDto>> list(@RequestParam("openid") String openid, @RequestParam(value = "page",defaultValue = "0") Integer page, @RequestParam(value = "size",defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDto> list = orderService.findList(openid, request);
        return ResultVoUtil.success(list.getContent());
    }


    @GetMapping("detail")
    public ResultVo<OrderDto> detail(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){



        return ResultVoUtil.success(buyerService.findOrderOne(openid,orderId));

    }

    @PostMapping("cancel")
    public ResultVo<OrderDto> cancel(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){

        OrderDto orderDto = orderService.findOne(orderId);

        orderService.cancel(orderDto);

        return ResultVoUtil.success(orderDto);

    }
}
