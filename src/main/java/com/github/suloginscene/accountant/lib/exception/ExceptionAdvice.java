package com.github.suloginscene.accountant.lib.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> on(BindException e) {
        log.warn(toLogString(e));
        return ResponseEntity
                .badRequest().body(ErrorResponse.of(e));
    }

    private String toLogString(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + "=" + f.getRejectedValue())
                .collect(joining(","));
        return "BindException(" + message + ")";
    }


    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorResponse> on(RequestException e) {
        log.warn(toLogString(e));
        return ResponseEntity
                .badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Void> on(ForbiddenException e) {
        log.warn(toLogString(e));
        return ResponseEntity
                .status(FORBIDDEN).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> on(NotFoundException e) {
        log.warn(toLogString(e));
        return ResponseEntity
                .status(NOT_FOUND).build();
    }


    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Void> on(InternalException e) {
        log.error(toLogString(e));
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR).build();
    }

    private String toLogString(Exception e) {
        String className = e.getClass().getSimpleName();
        String message = e.getMessage();
        return className + "(" + message + ")";
    }

}
