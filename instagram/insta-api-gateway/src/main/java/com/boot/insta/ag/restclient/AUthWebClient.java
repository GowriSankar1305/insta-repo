package com.boot.insta.ag.restclient;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AUthWebClient {
	
	@Autowired
	private WebClient.Builder builder;
	
	public Boolean isTokenBlackListed(Map<String, String> headers)	{
	//	log.info("headers map--------->> {}",headers);
		HttpHeaders reqHeaders = new HttpHeaders();
		headers.forEach((k,v) -> reqHeaders.add(k, v));
	//	log.info("token ##################### {}",reqHeaders.getFirst("Authorization"));
	//	log.info("uname ##################### {}",reqHeaders.getFirst("userName"));
	//	log.info("login tkn ##################### {}",reqHeaders.getFirst("loginToken"));
	final AtomicBoolean result = new AtomicBoolean();	
		Disposable resp = builder
				.build()
				.post()
				.uri("http://auth-service/auth/check-token")
				.headers(header -> header.addAll(reqHeaders))
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(headers), Map.class)
				.retrieve()
				.bodyToMono(Boolean.class)
				.map(transform)
				.subscribe(mono -> result.set(mono));
	//	log.info("disaposable response------> {}",resp.toString());
		return result.get();		
	}
	
	private Function<Boolean, Boolean> transform = (flag) -> {
		return flag;
	};
}
