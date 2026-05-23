package com.example.demo.validation;

import com.example.demo.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailPhoneMatchValidator implements ConstraintValidator<EmailPhoneMatch, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null || user.getEmail() == null || user.getPhoneNumber() == null) {
            return true; 
        }
        return user.getEmail().equals(user.getPhoneNumber());
    }
}
