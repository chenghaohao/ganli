package com.ganli.controller;

import com.ganli.common.entity.ResponseMessage;
import com.ganli.common.util.Constants;
import com.ganli.common.util.SendMsgUtil;
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
    @RequestMapping("exception")
    public Object exception(String callback){
        rm = new ResponseMessage();
        rm.setCode("000001");
        rm.setMsg("请求接口异常");
        return jsonpHandler(rm,callback);
    }
    @RequestMapping("test")
    public Object test(String callback){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("姓名","程浩");
        map.put("职位","开发工程师");
//        System.out.print(Constants.MSG_URL);
//        SendMsgUtil.sendMsg("13408686073","礼金理账号注册手机验证码:2333");
        return jsonpHandler(map,callback);
    }
}
