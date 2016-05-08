package com.ganli.common.util;

public class HttpStatusErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701947438073791670L;
	
	public HttpStatusErrorException() {
		super();
	}
	
	public HttpStatusErrorException(String message) {
		super(message);
	}

}
