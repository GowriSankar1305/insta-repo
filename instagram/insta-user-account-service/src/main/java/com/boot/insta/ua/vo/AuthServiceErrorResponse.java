package com.boot.insta.ua.vo;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthServiceErrorResponse implements Serializable	{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9189218519715041150L;
	private String token;
	private String userName;
	private String userId;
	private Map<String, String> errors;
	@NonNull
	private String errorMsg;
	private String userProfileId;
	private String userAccId;
}
