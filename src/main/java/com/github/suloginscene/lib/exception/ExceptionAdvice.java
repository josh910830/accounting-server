package com.github.suloginscene.lib.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> on(Exception e) {
        log.error(toLogString(e));
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR).build();
    }

    private String toLogString(Exception e) {
        String className = e.getClass().getSimpleName();
        String message = e.getMessage();
        return className + "(" + message + ")";
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> on(BindException e) {
        String message = joinMappedFiledErrorSentences(e);
        return on(new RequestException(message));
    }

    private String joinMappedFiledErrorSentences(BindException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + "=" + f.getRejectedValue())
                .collect(joining(","));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> on(HttpMessageNotReadableException e) {
        if (isRequiredRequestBodyMissing(e)) {
            return on(new RequestException("required request body is missing"));
        }

        return on(new InternalException("http message not readable while required request body is present"));
    }

    private boolean isRequiredRequestBodyMissing(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        return message != null && message.startsWith("Required request body is missing");
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> on(MethodArgumentTypeMismatchException e) {
        if (isNullArgument(e)) {
            return on(new RequestException(e.getName() + " is null"));
        }

        return on(new InternalException("method argument type mismatch while non null"));
    }

    private boolean isNullArgument(MethodArgumentTypeMismatchException e) {
        Object value = e.getValue();
        return value == null || value.equals("null");
    }

}
