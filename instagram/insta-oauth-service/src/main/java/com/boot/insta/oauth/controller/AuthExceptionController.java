package com.boot.insta.oauth.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.boot.insta.oauth.exception.CustomRunTimeException;
import com.boot.insta.oauth.exception.PrivateKeyNotFoundException;
import com.boot.insta.oauth.service.UserLoginService;
import com.boot.insta.oauth.vo.AuthServiceApiResponseVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class AuthExceptionController {
	
	@Autowired
	private UserLoginService userLoginService;
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handleBeanValidationException
	(MethodArgumentNotValidException ex,HttpServletRequest req) {
		log.error("<<<<<<<<<<<<<<<<< validation error : {}",ex.getMessage());
		Map<String, String> errors = ex.getFieldErrors().stream().collect(
				Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		AuthServiceApiResponseVo response = new AuthServiceApiResponseVo();
		response.setErrors(errors);
		response.setErrorMsg("");
		response.setUserProfileId(req.getParameter("profileId"));
		response.setUserAccId(req.getParameter("accId"));
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = PrivateKeyNotFoundException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handlePrivateKeyNotFoundException
	(PrivateKeyNotFoundException e,HttpServletRequest req) {
		log.error("<<<<<<<<<<<<<<<<< rsa key not found : {}", e);
		userLoginService.recordLoginActivity(req, Boolean.FALSE,"PrivateKeyNotFoundException");
		Map<String, String> error = new HashMap<>();
		error.put("message", "Problem while generating Auth token");
		AuthServiceApiResponseVo response = new AuthServiceApiResponseVo();
		response.setErrors(error);
		response.setErrorMsg("");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NoSuchAlgorithmException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handleNoSuchAlgorithmException
	(NoSuchAlgorithmException e,HttpServletRequest req) {
		log.error("<<<<<<<<<<<<<<<<< Invalid encryption algorithm : {}", e);
		userLoginService.recordLoginActivity(req, Boolean.FALSE,"NoSuchAlgorithmException");
		Map<String, String> error = new HashMap<>();
		error.put("message", "Problem while generating Auth token");
		AuthServiceApiResponseVo response = new AuthServiceApiResponseVo();
		response.setErrors(error);
		response.setErrorMsg("");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = InvalidKeySpecException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handleInvalidKeySpecException(InvalidKeySpecException e) {
		log.error("<<<<<<<<<<<<<<<<< Invalid private key specification : {}", e);
		Map<String, String> error = new HashMap<>();
		error.put("message", "Problem while generating Auth token");
		AuthServiceApiResponseVo response = new AuthServiceApiResponseVo();
		response.setErrors(error);
		response.setErrorMsg("");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handleRunTimeException(RuntimeException re)	{
		log.error("<<<<<<<<<<<<<<<<<<< RunTimeException occurred : {}",re);
		return new ResponseEntity<>
		(new AuthServiceApiResponseVo("Internal Error in Authentication service!"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = CustomRunTimeException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handleCustomRunTimeException(CustomRunTimeException re)	{
		log.error("<<<<<<<<<<<<<<<<<<< CustomRunTimeException occurred : {}",re);
		return new ResponseEntity<>
		(new AuthServiceApiResponseVo(re.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<AuthServiceApiResponseVo> handleBadCredentialsException
	(BadCredentialsException be,HttpServletRequest req)	{
		userLoginService.recordLoginActivity(req, Boolean.FALSE,"BadCredentialsException");
		log.error("<<<<<<<<<<<<<<<<<<< BadCredentialsException occurred : {}",be);
		return new ResponseEntity<>
		(new AuthServiceApiResponseVo("Invalid username or password"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
