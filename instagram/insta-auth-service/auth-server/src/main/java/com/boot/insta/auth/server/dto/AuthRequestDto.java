package com.boot.insta.auth.server.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6625251690949295221L;
	
	@NotBlank(message = "user name is empty")
	private String userName;
	@NotBlank(message = "password is empty")
	private String password;
}
