package com.boot.insta.ag.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Order(-2)
@Component
public class GlobalWebExceptionErrorHandler extends AbstractErrorWebExceptionHandler {

	public GlobalWebExceptionErrorHandler(GlobalErrorAttributes globalErrAttr,
			ApplicationContext appCtx,ServerCodecConfigurer cfgr)	{
		super(globalErrAttr, new WebProperties.Resources(), appCtx);
		super.setMessageWriters(cfgr.getWriters());
		super.setMessageReaders(cfgr.getReaders());
	}
	
	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}
	
	private Mono<ServerResponse> renderErrorResponse(ServerRequest request)	{
		Map<String, Object> errorMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
		HttpStatus status = HttpStatus.valueOf((Integer)errorMap.get("status"));
		return ServerResponse.
				status(status)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(errorMap));
	}
}
