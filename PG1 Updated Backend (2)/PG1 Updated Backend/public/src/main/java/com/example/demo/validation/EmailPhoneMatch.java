package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailPhoneMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailPhoneMatch {
    String message() default "Email and phone number must be the same";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}  