package com.github.suloginscene.lib.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;


@Data
@AllArgsConstructor(access = PRIVATE)
public class ErrorResponse {

    private final String error;
    private final String errorDescription;


    public static ErrorResponse of(RequestException e) {
        String className = e.getClass().getSimpleName();
        String message = e.getMessage();
        return new ErrorResponse(className, message);
    }

}
