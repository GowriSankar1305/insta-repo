package com.boot.insta.ag.feignclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

@Configuration
public class AuthFeignClientConfig {
	
	@Bean
	public Decoder<?> getDecoder() {
		return new Jackson2JsonDecoder(); 
	}
	
	@Bean
	public Encoder<?> getEncoder()	{
		return new Jackson2JsonEncoder();
	}
}
