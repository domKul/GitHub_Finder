package com.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException userNotFoundException) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status ", HttpStatus.NOT_FOUND.value());
        errorResponse.put("Message ", userNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<Object> handleMediaTypeNotAcceptableException() {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Only JSON is accepted");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }


}
