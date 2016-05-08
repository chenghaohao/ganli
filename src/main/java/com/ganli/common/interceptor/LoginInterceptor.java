package com.ganli.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * Copyright (c) 2015, 北京卡拉卡尔科技股份有限公司公司 All rights reserved.
 *
 * @description 登录验证拦截器
 * @author hao.cheng@karakal.com.collapse-nav
 * @date 2015年6月8日 上午11:41:26
 */
public class LoginInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String ip = request.getRemoteAddr();
		String uri = request.getRequestURI();
		String param = "";
		Map<String,Object> map = request.getParameterMap();
		for(Iterator iter = map.entrySet().iterator();iter.hasNext();){
			Map.Entry element = (Map.Entry)iter.next();
			Object strKey = element.getKey();
			String[] value=(String[])element.getValue();
			param += strKey.toString() + "=";
			for(int i=0;i<value.length;i++){
				param += value[i] + ",";
			}
		}
		logger.info("请求来源："+ip+"/"+uri+"?"+param);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		log.info("---------登录拦截器执行2---------");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		log.info("---------登录拦截器执行3---------");
	}

}
