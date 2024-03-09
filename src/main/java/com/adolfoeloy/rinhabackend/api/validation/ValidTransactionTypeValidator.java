package com.adolfoeloy.rinhabackend.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTransactionTypeValidator implements ConstraintValidator<ValidTransactionType, Character> {
    @Override
    public boolean isValid(Character character, ConstraintValidatorContext constraintValidatorContext) {
        return (character == 'c' || character == 'd');
    }
}
