package com.boot.insta.ua.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.insta.ua.service.UserProfileService;

@Component
public class EmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserProfileService userProfileService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return null == userProfileService.getUserByEmail(value);
	}
	
}
