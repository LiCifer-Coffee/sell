package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMaster2OrderDtoConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.respository.OrderDetailRespository;
import com.imooc.sell.respository.OrderMasterRespository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRespository orderMasterRespository;

    @Autowired
    private OrderDetailRespository orderDetailRespository;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        String orderId = KeyUtil.getUniqueKey();
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()) {

            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }


            BeanUtils.copyProperties(productInfo,orderDetail);

            orderAmount = orderDetail.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            orderDetail.setOrderId(orderId);

            orderDetail.setDetailId(KeyUtil.getUniqueKey());


            orderDetailRespository.save(orderDetail);
        }


        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRespository.save(orderMaster);


        List<CartDto> cartDtos = orderDto.getOrderDetailList().stream().map(e->new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        productService.decreaseStock(cartDtos);
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRespository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> details = orderDetailRespository.findByOrderId(orderMaster.getOrderId());
        if (CollectionUtils.isEmpty(details)) {

            throw new SellException(ResultEnum.ORDER_DETAIL_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(details);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRespository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDto> orderDtos = OrderMaster2OrderDtoConverter.converter(orderMasters.getContent());
        return new PageImpl<>(orderDtos,pageable,orderMasters.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);

        if (orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消订单】订单状态不正确，orderId:{}", orderDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRespository.save(orderMaster);

        if (updateResult == null) {
            log.info("【取消订单】更新失败");
            throw new SellException(ResultEnum.ORDER_CANCEL_FAIL);
        }

        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.info("【取消订单】订单详情为空");
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDto> cartDtos = orderDto.getOrderDetailList().stream().map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());

        productService.increaseStock(cartDtos);

        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        if (orderDto.getOrderStatus().equals(OrderStatusEnum.NEW)) {
            log.info("【完结订单】更新失败");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        OrderMaster orderMaster = new OrderMaster();

        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);

        OrderMaster master = orderMasterRespository.save(orderMaster);
        if (master == null) {
            log.info("【完结订单】更新订单状态失败");
            new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {

        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【订单支付】订单支付失败");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }


        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.info("【订单支付】订单支付状态不正确");
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());


        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);

        OrderMaster master = orderMasterRespository.save(orderMaster);
        if (master == null) {
            log.info("【订单支付】订单支付保存失败");
            new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }
}
