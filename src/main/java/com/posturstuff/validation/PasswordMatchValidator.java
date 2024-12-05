package com.posturstuff.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    private String password;
    private String passwordConfirmation;

    @Override
    public void initialize(PasswordMatch passwordMatch) {
        ConstraintValidator.super.initialize(passwordMatch);
        password = passwordMatch.password();
        passwordConfirmation = passwordMatch.passwordConfirmation();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Field passwordField = object.getClass().getDeclaredField(password);
            Field passwordConfirmationField = object.getClass().getDeclaredField(passwordConfirmation);

            passwordField.setAccessible(true);
            passwordConfirmationField.setAccessible(true);

            String password = (String) passwordField.get(object);
            String passwordConfirmation = (String) passwordConfirmationField.get(object);

            if(password != null && password.equals(passwordConfirmation)) {
                return true;
            }

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("passwordConfirmation")
                    .addConstraintViolation();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
