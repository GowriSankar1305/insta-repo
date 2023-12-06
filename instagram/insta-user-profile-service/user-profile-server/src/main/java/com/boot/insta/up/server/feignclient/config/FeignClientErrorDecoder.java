package com.boot.insta.up.server.feignclient.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.boot.insta.up.server.annotation.HandleFeignError;

import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignClientErrorDecoder implements ErrorDecoder {
	
	@Autowired
	private ApplicationContext appContext;
	private final Map<String, FeignExceptionHandler> exceptionHandlerMap = new HashMap<>();
	private final ErrorDecoder defaultDecoder = new Default();
	
	@Override
	public Exception decode(String methodKey, Response response) {
		FeignExceptionHandler handler = exceptionHandlerMap.get(methodKey);
		if(handler != null)	{
			return handler.handle(response);
		}
		return defaultDecoder.decode(methodKey, response);
	}
	
	@EventListener
	public void configureExceptionHandlers(ContextRefreshedEvent event)	{
		Map<String, Object> feignClients = appContext.getBeansWithAnnotation(FeignClient.class);
		List<Method> clientMethods = feignClients.values().stream()
				.map(Object::getClass)
				.map(clz -> clz.getInterfaces()[0])
				.map(ReflectionUtils::getDeclaredMethods)
				.flatMap(Arrays::stream)
				.collect(Collectors.toList());
		for(Method m : clientMethods)	{
			String configKey = Feign.configKey(m.getDeclaringClass(), m);
			HandleFeignError handlerAnnotation = getErrorAnnotation(m);
			if(handlerAnnotation != null)	{
				FeignExceptionHandler handler = appContext.getBean(handlerAnnotation.value());
				exceptionHandlerMap.put(configKey, handler);
			}
		}
	}
	
	private HandleFeignError getErrorAnnotation(Method m)	{
		return m.getAnnotation(HandleFeignError.class);
	}
}
