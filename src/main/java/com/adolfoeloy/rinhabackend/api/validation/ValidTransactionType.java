package com.adolfoeloy.rinhabackend.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTransactionTypeValidator.class)
@Documented
public @interface ValidTransactionType {
    String message() default "Transaction type must be either c for credit or d for debit";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
