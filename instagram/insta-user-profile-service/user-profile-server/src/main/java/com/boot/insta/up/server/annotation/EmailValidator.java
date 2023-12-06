package com.boot.insta.up.server.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.insta.up.server.service.UserProfileService;

@Component
public class EmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserProfileService userProfileService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return null == userProfileService.getUserByEmail(value);
	}
	
}
