package com.adolfoeloy.rinhabackend.infra;

import com.adolfoeloy.rinhabackend.domain.exception.CustomerLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CustomerLimitExceededException.class)
    public void handleLimitExceededException() {
    }

}
