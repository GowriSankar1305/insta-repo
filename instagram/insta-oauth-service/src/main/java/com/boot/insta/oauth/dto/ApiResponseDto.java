package com.boot.insta.oauth.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6426600204735572829L;
	
	private String token;
	private String userName;
	private String userId;
	private String userProfileId;
	private String userAccountId;
	private Map<String, String> errors;
}
