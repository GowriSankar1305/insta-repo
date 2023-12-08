package com.boot.insta.ua.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512936439611472242L;
	
	private String message;
	private int statusCode;
	private Map<String, String> errors;
	
	public ApiResponseDto(String msg,int code)	{
		this.message = msg;
		this.statusCode = code;
	}
}
