package com.boot.insta.ag.service;

import com.boot.insta.ag.dto.TokenValidationDto;

public interface AuthTokenService {
	public boolean isAuthTokenValid(TokenValidationDto dto,String reqPath);
}
