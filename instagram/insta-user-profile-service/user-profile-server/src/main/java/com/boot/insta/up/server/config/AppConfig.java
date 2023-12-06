package com.boot.insta.up.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
	
	@Bean
	public ObjectMapper getObjectMapper()	{
		return new ObjectMapper();
	}
}
