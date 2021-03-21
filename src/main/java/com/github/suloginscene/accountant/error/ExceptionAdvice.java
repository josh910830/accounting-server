package com.github.suloginscene.accountant.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> on(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return badRequestWithLogWarn(errorResponse);
    }

    // TODO account cast exception -> badRequest
    // TODO negative money exception -> badRequest

    private ResponseEntity<ErrorResponse> badRequestWithLogWarn(ErrorResponse errorResponse) {
        log.warn(errorResponse.toString());
        return ResponseEntity.badRequest().body(errorResponse);
    }


    private ResponseEntity<ErrorResponse> forbiddenWithLogWarn(ErrorResponse errorResponse) {
        log.warn(errorResponse.toString());
        return ResponseEntity.status(FORBIDDEN).body(errorResponse);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> on(NoSuchElementException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return notFoundWithLogWarn(errorResponse);
    }

    private ResponseEntity<ErrorResponse> notFoundWithLogWarn(ErrorResponse errorResponse) {
        log.warn(errorResponse.toString());
        return ResponseEntity.status(NOT_FOUND).build();
    }

}
