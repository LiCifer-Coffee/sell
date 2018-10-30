package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "扣库存失败"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_CANCEL_FAIL(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    ORDER_UPDATE_FAIL(17, "更新订单状态失败"),
    ORDER_PAY_STATUS_ERROR(18,"订单支付状态不正确")
    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
