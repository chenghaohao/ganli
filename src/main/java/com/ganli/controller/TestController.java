package com.ganli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hao.cheng on 2016/2/23.
 * test
 */
@RestController
@RequestMapping()
public class TestController extends BaseController{
    @RequestMapping("test")
    public Object test(String callback){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("姓名","程浩");
        map.put("职位","开发工程师");
        return jsonpHandler(map,callback);
    }
}
