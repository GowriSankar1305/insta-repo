package com.boot.insta.ag.config;

import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Bean
	public CorsWebFilter corsFilter()	{
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedHeaders(List.of("*"));
		corsConfig.setAllowedMethods(List.of("*"));
		corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return new CorsWebFilter(source);
	}
	
	//@Bean
	//@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@LoadBalanced
	public WebClient.Builder getBuilder()	{
		return WebClient.builder();
	}
}
