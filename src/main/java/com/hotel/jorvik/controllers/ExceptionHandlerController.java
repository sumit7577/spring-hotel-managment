package com.hotel.jorvik.controllers;

import com.hotel.jorvik.response.ErrorResponse;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Response> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(ex.getBindingResult().toString()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolation(ConstraintViolationException ex) {
        Set<String> violations = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>(violations));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> handleException(Exception e) {
        String message = e.getMessage() == null ? e.toString() : e + " " + e.getMessage();
        message = message + "\n" + Arrays.toString(e.getStackTrace());
        message = message.length() > 1000 ? message.substring(0, 1000) : message;
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(message));
    }
}
