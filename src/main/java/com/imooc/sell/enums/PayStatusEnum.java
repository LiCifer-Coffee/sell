package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    WAIT(1,"等待支付"),
    SUCCESS(2,"支付成功")
    ;


    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
