package com.boot.insta.auth.server.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UserNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface UniqueUserName {
	public String message() default "username already exists";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}
