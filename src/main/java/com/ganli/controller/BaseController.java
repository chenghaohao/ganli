package com.ganli.controller;

import com.alibaba.fastjson.JSONPObject;
import com.ganli.common.entity.ResponseMessage;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * Created by hao.cheng on 2016/2/23.
 */
public class BaseController {
    public ResponseMessage rm = new ResponseMessage();
    /**
     * JSONP跨域请求处理
     * @param o
     * @param callback
     * @return
     */
    public Object jsonpHandler(Object o,String callback){
        if(StringUtils.isBlank(callback)){
            return o;
        }
        JSONPObject jsonp = new JSONPObject(callback);
        jsonp.addParameter(o);
        return jsonp;
    }
}
