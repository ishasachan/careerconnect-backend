package com.careerconnect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.careerconnect.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        String message = ex.getMessage();
        HttpStatus status;

        if (message.contains("Email already exists")) {
            status = HttpStatus.CONFLICT; // 409
        } else if (message.contains("User not found") || message.contains("Wrong password")) {
            status = HttpStatus.UNAUTHORIZED; // 401
        } else {
            status = HttpStatus.BAD_REQUEST; // 400
        }

        ErrorResponse error = new ErrorResponse(message, status.value());
        return new ResponseEntity<>(error, status);
    }
}
