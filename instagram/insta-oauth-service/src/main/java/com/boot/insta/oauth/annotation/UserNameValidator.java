package com.boot.insta.oauth.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.insta.oauth.service.UserService;

@Component
public class UserNameValidator implements ConstraintValidator<UniqueUserName, String>{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return null == userService.getUserByUserName(value);
	}
}
