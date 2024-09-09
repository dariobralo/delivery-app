package com.delivery_api.validation;

import com.delivery_api.web.request.AuthCreatedUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        AuthCreatedUserRequest userRequest = (AuthCreatedUserRequest) object;

        return userRequest.password().equals(userRequest.matchingPassword());
    }
}
