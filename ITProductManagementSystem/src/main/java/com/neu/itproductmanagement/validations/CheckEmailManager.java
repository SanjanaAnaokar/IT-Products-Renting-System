package com.neu.itproductmanagement.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = CheckEmailManagerConstraintValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEmailManager {
	public String message() default " EmailID Already Exists";
	
	public Class<?>[] groups() default{};
	
	public Class<? extends Payload>[] payload() default{};
}
