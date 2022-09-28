package com.mayikt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class MayiktController {

   @Value("${server.port}")
    private String host;

    @RequestMapping("/getMember")
    public String getMember(){
        return "我是会员服务"+host+"，我提供接口";
    }
}
