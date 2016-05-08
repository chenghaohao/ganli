package com.ganli.common.util;

/**
 * Copyright (c) 2015, 北京卡拉卡尔科技股份有限公司 All rights reserved.
 *
 * @Description: 响应体为空异常 
 * @since
 * @author xiaolong.li@karakal.com.cn (李小龙)   
 * @date 2015年8月18日 下午5:47:30 
 * @version 1.0
 */
public class HttpEntityNullException extends Exception {

	private static final long serialVersionUID = -4271047424364284692L;
	
	public HttpEntityNullException() {
		super();
	}
	
	public HttpEntityNullException(String message) {
		super(message);
	}

}
