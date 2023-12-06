package com.boot.insta.up.server.authservice.feignclient;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.boot.insta.auth.client.vo.AuthServiceApiResponseVo;
import com.boot.insta.up.server.exception.AuthServiceException;
import com.boot.insta.up.server.feignclient.config.FeignExceptionHandler;
import com.boot.insta.up.server.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;

@Component
public class SaveUserFClientExceptionHandler implements FeignExceptionHandler {
	
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private UserProfileService userProfileService;
	
	@Override
	public Exception handle(Response response) {
		HttpStatus status = HttpStatus.resolve(response.status());
		try {
			AuthServiceApiResponseVo resp = mapper.readValue(
					response.body().asInputStream(), AuthServiceApiResponseVo.class);
			userProfileService.deleteUserProfileAndAccount(
					Long.parseLong(resp.getUserProfileId()) ,Long.parseLong(resp.getUserAccId()));
			return new AuthServiceException(resp, status);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
