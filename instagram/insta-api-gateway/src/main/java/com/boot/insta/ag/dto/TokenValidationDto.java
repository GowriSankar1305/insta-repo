package com.boot.insta.ag.dto;

import lombok.Data;

@Data
public class TokenValidationDto {
	private String authToken;
	private String loginToken;
	private String userName;
	private String publicKey;
}
