package com.boot.insta.ua.exception;

import lombok.Getter;

@Getter
public class ProfilePicUploadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2497648258056139943L;
	
	private String message;
	
	public ProfilePicUploadException(String message)	{
		this.message = message;
	}
}
