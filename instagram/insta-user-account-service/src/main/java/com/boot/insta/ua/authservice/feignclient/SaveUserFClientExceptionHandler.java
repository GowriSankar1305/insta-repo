package com.boot.insta.ua.authservice.feignclient;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.boot.insta.ua.exception.AuthServiceException;
import com.boot.insta.ua.feignclient.config.FeignExceptionHandler;
import com.boot.insta.ua.service.UserProfileService;
import com.boot.insta.ua.vo.AuthServiceErrorResponse;
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
			AuthServiceErrorResponse resp = mapper.readValue(
					response.body().asInputStream(), AuthServiceErrorResponse.class);
			userProfileService.deleteUserProfileAndAccount(
					Long.parseLong(resp.getUserProfileId()) ,Long.parseLong(resp.getUserAccId()));
			return new AuthServiceException(resp, status);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
