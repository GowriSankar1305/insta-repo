package com.boot.insta.up.server.dto;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomRequestHeadersDto {
	
	private static final String USR_NAME = "userName";
	private static final String PRFL_ID = "userProfileId";
	private static final String ACC_ID = "userAccountId";
	private static final String LOGIN_TKN = "loginToken";
	
	private String userName;
	private String userProfileId;
	private String userAccountId;
	private String loginToken;
	
	public static CustomRequestHeadersDto populateData(HttpServletRequest request)	{
		CustomRequestHeadersDto dto = new CustomRequestHeadersDto();
		dto.setLoginToken(request.getHeader(LOGIN_TKN));
		dto.setUserAccountId(request.getHeader(ACC_ID));
		dto.setUserName(request.getHeader(USR_NAME));
		dto.setUserProfileId(request.getHeader(PRFL_ID));
		return dto;
	}
}
