package com.github.suloginscene.accountant.lib.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import static java.util.stream.Collectors.joining;
import static lombok.AccessLevel.PRIVATE;


@Data
@AllArgsConstructor(access = PRIVATE)
public class ErrorResponse {

    private final String error;
    private final String errorDescription;


    public static ErrorResponse of(BindException e) {
        String className = "BindException";
        String message = e.getBindingResult()
                .getFieldErrors().stream()
                .map(ErrorResponse::formatFieldError)
                .collect(joining(","));
        return new ErrorResponse(className, message);
    }

    private static String formatFieldError(FieldError fe) {
        return String.format("%s=%s(%s)",
                fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage());
    }


    public static ErrorResponse of(RequestException e) {
        String className = e.getClass().getSimpleName();
        String message = e.getMessage();
        return new ErrorResponse(className, message);
    }

}
