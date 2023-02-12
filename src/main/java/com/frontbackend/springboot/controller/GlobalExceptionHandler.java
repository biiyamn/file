package com.frontbackend.springboot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex) {
        String error = "Malformed JSON request";

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}