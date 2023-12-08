package com.boot.insta.ua.exception;

import org.springframework.http.HttpStatus;

import com.boot.insta.ua.vo.AuthServiceErrorResponse;

import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 834972981964232322L;
	
	private AuthServiceErrorResponse resp;
	private HttpStatus httpStatus;
	
	public AuthServiceException(AuthServiceErrorResponse resp,HttpStatus httpStatus)	{
		this.resp = resp;
		this.httpStatus = httpStatus;
	}
}
