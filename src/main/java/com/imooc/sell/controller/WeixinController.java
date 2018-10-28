package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weixin")
@Slf4j
public class WeixinController {

    @GetMapping("auth")
    public void weixinAuth(@RequestParam("code") String code){
        log.info("code:{}",code);
    }


}
