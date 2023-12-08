package com.boot.insta.ua.feignclient.config;

import feign.Response;

public interface FeignExceptionHandler {
	Exception handle(Response response);
}
