package com.boot.insta.ua.authservice.feignclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.insta.ua.annotation.HandleFeignError;

@FeignClient(name = "auth-service",path = "/auth")
public interface AuthServiceClient {
	
	@HandleFeignError(value = SaveUserFClientExceptionHandler.class)
	@PostMapping("/register")
	ResponseEntity<?> saveUserDetails(@RequestBody Map<String, String> reqPayload,
			@RequestParam(name = "profileId") String pId,@RequestParam(name = "accId") String aId);
}
