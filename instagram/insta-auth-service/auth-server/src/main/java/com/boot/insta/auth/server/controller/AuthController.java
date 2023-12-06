package com.boot.insta.auth.server.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.insta.auth.server.dto.AuthRequestDto;
import com.boot.insta.auth.server.dto.UserDto;
import com.boot.insta.auth.server.exception.PrivateKeyNotFoundException;
import com.boot.insta.auth.server.service.UserLoginService;
import com.boot.insta.auth.server.service.UserService;
import com.boot.insta.auth.server.util.CacheUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CacheUtil cacheUtil;
	@Autowired
	private UserLoginService userLoginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(
			@RequestBody @Valid AuthRequestDto reqPayload,
			HttpServletRequest request) throws NoSuchAlgorithmException, 
							InvalidKeySpecException, PrivateKeyNotFoundException {
		Map<String, String> responseMap = userService.loginUser(reqPayload);
		userLoginService.recordLoginActivity(request, Boolean.TRUE,"Login successful");
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto userDto) {
		log.info("<------ AuthController.registerUser() ------->");
		return new ResponseEntity<>(userService.saveUser(userDto),HttpStatus.CREATED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(
			@RequestHeader(name = "Authorization") String token,
			@RequestHeader String userName,
			@RequestHeader String loginToken)	{
		log.info(" <-------------- logging out the user ------------->");
		return new ResponseEntity<>(cacheUtil.addTokenToCache(token, userName, loginToken),HttpStatus.OK);
	}
	
	@PostMapping("/check-token")
	public ResponseEntity<Boolean> isTokenBlackListed(@RequestHeader String userName,
			@RequestHeader String loginToken){
		return new ResponseEntity<>(
				cacheUtil.isTokenBlackListed(userName, loginToken),HttpStatus.OK);
	}
}
