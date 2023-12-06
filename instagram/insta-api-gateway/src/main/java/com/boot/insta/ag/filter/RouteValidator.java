package com.boot.insta.ag.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	
	@Value("${app.open.endpoints}")
	public List<String> openEndpoints; 
	
	public Predicate<String> isSecureRoute = route -> !openEndpoints.contains(route);
}
