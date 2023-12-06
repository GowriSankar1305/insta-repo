package com.boot.insta.up.server.exception;

import org.springframework.http.HttpStatus;

import com.boot.insta.auth.client.vo.AuthServiceApiResponseVo;

import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 834972981964232322L;
	
	private AuthServiceApiResponseVo resp;
	private HttpStatus httpStatus;
	
	public AuthServiceException(AuthServiceApiResponseVo resp,HttpStatus httpStatus)	{
		this.resp = resp;
		this.httpStatus = httpStatus;
	}
}
