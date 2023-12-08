package com.boot.insta.oauth.exception;

import lombok.Getter;

@Getter
public class PrivateKeyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3264703900996194727L;
	
	private String message;
	
	public PrivateKeyNotFoundException() {
		super();
	}
	
	public PrivateKeyNotFoundException(String message)	{
		super(message);
	}
}
