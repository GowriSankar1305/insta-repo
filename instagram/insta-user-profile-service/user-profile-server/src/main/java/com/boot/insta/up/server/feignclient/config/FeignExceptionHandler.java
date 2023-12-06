package com.boot.insta.up.server.feignclient.config;

import feign.Response;

public interface FeignExceptionHandler {
	Exception handle(Response response);
}
