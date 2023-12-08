package com.boot.insta.oauth.service;

import javax.servlet.http.HttpServletRequest;

public interface UserLoginService {
	public void recordLoginActivity(HttpServletRequest req,Boolean isLoginSuccess,String loginMsg);
}
