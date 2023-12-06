package com.boot.insta.ag.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.boot.insta.ag.dto.TokenValidationDto;
import com.boot.insta.ag.service.AuthTokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApiGatewayFilter extends AbstractGatewayFilterFactory<ApiGatewayFilter.Config> {
	
	@Autowired
	private RouteValidator routeValidator;
	@Autowired
	private AuthTokenService authTokenService;
	
	public ApiGatewayFilter()	{
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange,chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			log.info("********req url******* : {}",request.getURI().getPath());
			if(routeValidator.isSecureRoute.test(request.getURI().getPath()))	{
				log.info("<<------------ in ----------------->>");
				List<String> headers = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
				if(CollectionUtils.isEmpty(headers))	{
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Authorization header is missing in the request!");
				}
				String authtoken = headers.get(0);
			//	log.info("auth tkn------> " + authtoken);
				if(!StringUtils.hasText(authtoken))	{
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Auth token not present in header!");
				}
				if(!authtoken.startsWith("Bearer "))	{
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Invalid token format!");
				}
				
				TokenValidationDto dto = new TokenValidationDto();
				dto.setAuthToken(authtoken.split(" ")[1]);
				dto.setLoginToken(request.getHeaders().get("loginToken").get(0));
				dto.setUserName(request.getHeaders().get("userName").get(0));
				//log.info("dto i filter------XXXXXXXX************  {}",dto.toString());
				if(!authTokenService.isAuthTokenValid(dto,request.getURI().getPath()))	{
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Auth token is not valid!");
				}
			}
			return chain.filter(exchange);
		});
	}
	
	public static class Config	{
		
	}
}
