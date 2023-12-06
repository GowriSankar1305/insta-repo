package com.boot.insta.up.server.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.boot.insta.up.server.feignclient.config.FeignExceptionHandler;

@Retention(RUNTIME)
@Target(METHOD)
public @interface HandleFeignError {
	Class<? extends FeignExceptionHandler> value();
}
