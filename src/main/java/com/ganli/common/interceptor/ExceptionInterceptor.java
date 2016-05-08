package com.ganli.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统全局异常拦截器
 *
 * @author hao.cheng
 * @version 1.0
 * @date 2015-10-10 13:40:41
 */
public class ExceptionInterceptor implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        System.out.println(ex);
        logger.info("Catch Exception: ",ex);    //日志记录捕获的异常
        return new ModelAndView("redirect:/exception");
    }
}
