package com.boot.insta.oauth.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.boot.insta.oauth.annotation.UniqueUserName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4745893360265411579L;
	private String userId;
	@UniqueUserName
	@NotBlank(message = "username is required")
	private String userName;
	@NotBlank(message = "password is required")
	private String password;
	@NotBlank(message = "user profile id is required")
	private String userProfileId;
	@NotBlank(message = "user acc id is required")
	private String userAccId;
}
