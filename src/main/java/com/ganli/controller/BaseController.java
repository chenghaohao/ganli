package com.ganli.controller;

import com.alibaba.fastjson.JSONPObject;
import com.ganli.common.entity.ResponseMessage;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hao.cheng on 2016/2/23.
 */
public class BaseController implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        if(response instanceof HttpServletResponse){
            HttpServletResponse alteredResponse = ((HttpServletResponse)response);
            addCorsHeader(alteredResponse);
        }

        filterChain.doFilter(request, response);
    }

    private void addCorsHeader(HttpServletResponse response){
        //TODO: externalize the Allow-Origin
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig)throws ServletException{}

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
