package com.mayikt.controller;

import com.mayikt.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;

@RestController
//@RefreshScope
@Scope("prototype")
@Slf4j
public class MayiktController {
    @Value("${mayikt.path}")
    private String path;

    public MayiktController(){
        log.info("<MayiktController无参构造方法执行。。。>");
    }

    @RequestMapping("/getWeather")
    public String getWeather(String city){
        String url="http://wthrcdn.etouch.cn/weather_mini";
        HashMap<String, String> map = new HashMap<>();
        map.put("city",city);
        return HttpClientUtils.doGet(url,map);
    }

    @RequestMapping("/getPath")
    public String getPath(){
        return path;
    }
}
