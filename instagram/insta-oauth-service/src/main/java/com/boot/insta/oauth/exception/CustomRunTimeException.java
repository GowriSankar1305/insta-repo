package com.boot.insta.oauth.exception;

import lombok.Getter;

@Getter
public class CustomRunTimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7858055204873258660L;
	private final String message;
	
	public CustomRunTimeException(String msg) {
		super(msg);
		this.message = msg;
	}
}
