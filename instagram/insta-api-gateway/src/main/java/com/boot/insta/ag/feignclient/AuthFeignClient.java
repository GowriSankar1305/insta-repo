package com.boot.insta.ag.feignclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.boot.insta.ag.feignclient.config.AuthFeignClientConfig;

import feign.HeaderMap;

@FeignClient(name = "auth-service",path = "/auth",configuration = AuthFeignClientConfig.class)
public interface AuthFeignClient {

	@PostMapping("/check-token")
	ResponseEntity<Boolean> isTokenBlacklisted(@HeaderMap Map<String, String> reqHeaders);
}
