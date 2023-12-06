package com.boot.insta.ag.config;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	
	private static final String ATTR_NAME = "org.springframework.boot.web.reactive.error.DefaultErrorAttributes.ERROR";
	
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Map<String, Object> errorResponse = super.getErrorAttributes(request, options);
		Optional<Object> errorAttribute = request.attribute(ATTR_NAME);
		Throwable rootException = (Throwable)errorAttribute.get();
		log.error("Exception occured in api gateway : {}",rootException);
		if(rootException instanceof ResponseStatusException)	{
			ResponseStatusException rstException = (ResponseStatusException) rootException;
			errorResponse.put("message", rstException.getReason());
		}
		return errorResponse; 
	}

}
