package com.boot.insta.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class OAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OAuthServiceApplication.class, args);
	}
}
