package com.boot.insta.ua.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.exception.AuthServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class UserProfileExceptionController {
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleInvalidInput(MethodArgumentNotValidException exception)	{
		log.error("<<<<<<<<< Invalid request payload found: {}",exception);
		Map<String, String> errorsMap = new HashMap<>();
		exception.getFieldErrors().stream().forEach(obj -> {
			errorsMap.put(obj.getField(), obj.getDefaultMessage());
		});
		return new ResponseEntity<>(errorsMap,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ApiResponseDto> handleSystemException(RuntimeException re)	{
		log.error("<<<<<<<<<<< System exception occurred : {}",re);
		ApiResponseDto response = new ApiResponseDto();
		response.setMessage("Unable to process the request at our end!");
		response.setStatusCode(500);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = AuthServiceException.class)
	public ResponseEntity<?> handleAuthServiceException(AuthServiceException exception)	{
		log.error("<<<<<<<<<<<< Exception from AuthService: {}",exception);
		HttpStatus status = exception.getHttpStatus();
		if(status.equals(HttpStatus.BAD_REQUEST))	{
			return new ResponseEntity<>(exception.getResp().getErrors(),status);
		}
		else	{
			ApiResponseDto response = new ApiResponseDto();
			response.setMessage(exception.getResp().getErrorMsg());
			response.setStatusCode(status.value());
			return new ResponseEntity<>(response,status);
		}
	}
}
