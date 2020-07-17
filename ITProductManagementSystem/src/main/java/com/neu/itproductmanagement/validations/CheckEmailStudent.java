package com.neu.itproductmanagement.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import com.neu.itproductmanagement.validations.CheckEmailStudentConstraintValidator;

@Constraint(validatedBy = CheckEmailStudentConstraintValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEmailStudent {

public String message() default " Email Id Already Exists";
	
	public Class<?>[] groups() default{};
	
	public Class<? extends Payload>[] payload() default{};
	
}
