package com.boot.insta.up.server.exception;

import lombok.Getter;

@Getter
public class ProfilePicDownloadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3368600838936311876L;

	private String message;
	
	public ProfilePicDownloadException(String message)	{
		this.message = message;
	}
}
