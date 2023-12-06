package com.boot.insta.auth.server.service.impl;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.boot.insta.auth.server.entity.UserLogin;
import com.boot.insta.auth.server.repository.UserLoginRepository;
import com.boot.insta.auth.server.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Override
	public void recordLoginActivity(HttpServletRequest req, Boolean isLoginSuccess,String loginMsg) {
		UserLogin userLogin = new UserLogin();
		userLogin.setHost(req.getHeader(HttpHeaders.HOST));
		userLogin.setIpAddress(req.getRemoteAddr());
		userLogin.setIsLoginSuccess(isLoginSuccess);
		userLogin.setRefererUrl(req.getHeader(HttpHeaders.REFERER));
		userLogin.setUserAgent(req.getHeader(HttpHeaders.USER_AGENT));
		userLogin.setPlatform(req.getHeader("Sec-Ch-Ua-Platform"));
		userLogin.setOriginUrl(req.getHeader(HttpHeaders.ORIGIN));
		userLogin.setMessage(loginMsg);
		userLogin.setLoggedInTime(LocalDateTime.now());
		userLogin.setUserName(req.getHeader("userName"));
		userLoginRepo.save(userLogin);
	}

}
