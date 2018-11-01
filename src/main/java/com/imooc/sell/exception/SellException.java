package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum){

        super(resultEnum.getMessage());
        code = resultEnum.getCode();

    }


    public SellException(Integer code, String defaultMessage) {

        super(defaultMessage);
        this.code = code;
    }
}
