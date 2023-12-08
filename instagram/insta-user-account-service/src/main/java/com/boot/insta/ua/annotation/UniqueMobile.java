package com.boot.insta.ua.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = MobileValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface UniqueMobile {
	public String message() default "Mobile number already exists!";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}
