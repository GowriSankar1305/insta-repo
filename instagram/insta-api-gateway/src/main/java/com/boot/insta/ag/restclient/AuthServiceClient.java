package com.boot.insta.ag.restclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthServiceClient {
	
	//@Autowired
	private RestTemplate restTemplate;
	
	public Boolean checkForBlackListedToken(Map<String, String> headers)	{
		HttpEntity<Map<String, String>> reqHeaders = new HttpEntity<>(headers);
		log.info("<<--- black list token api call --->>");
		ResponseEntity<Boolean> response = restTemplate
				.exchange("http://auth-service/auth/check-token", HttpMethod.POST, reqHeaders, Boolean.class);
		return response.getBody();
	}
}
