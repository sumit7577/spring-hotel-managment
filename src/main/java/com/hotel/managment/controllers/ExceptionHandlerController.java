package com.hotel.managment.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.hotel.managment.responses.ErrorResponse;
import com.hotel.managment.responses.FailResponse;
import com.hotel.managment.responses.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class serves as the controller advice for handling exceptions that may occur during API
 * requests. It provides methods to handle various types of exceptions, such as validation errors,
 * constraint violations, illegal arguments, and more. Each method returns an appropriate response
 * with error details.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Response> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    log.warn("MethodArgumentNotValidException occurred: {}", ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST)
        .body(new ErrorResponse(ex.getBindingResult().toString()));
  }

  /**
   * Handles ConstraintViolationException that may occur during API requests. This method captures
   * constraint violations, extracts their messages, and returns an appropriate response with error
   * details.
   *
   * @param ex The ConstraintViolationException that occurred.
   * @return A ResponseEntity with a FailResponse containing a set of constraint violation messages.
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Response> handleConstraintViolation(ConstraintViolationException ex) {
    log.warn("ConstraintViolationException occurred: {}", ex.getMessage());
    Set<String> violations =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toSet());
    return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>(violations));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Response> handleIllegalArgument(IllegalArgumentException ex) {
    log.warn("IllegalArgumentException occurred: {}", ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>(ex.getMessage()));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Response> handleNoSuchElement(NoSuchElementException ex) {
    log.warn("NoSuchElementException occurred: {}", ex.getMessage());
    return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>(ex.getMessage()));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<Response> handleUsernameNotFound(UsernameNotFoundException ex) {
    log.warn("UsernameNotFoundException occurred: {}", ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>(ex.getMessage()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Response> handleAccessDenied(AccessDeniedException ex) {
    log.warn("AccessDeniedException occurred: {}", ex.getMessage());
    return ResponseEntity.status(UNAUTHORIZED).body(new FailResponse<>(ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Response> handleException(Exception ex) {
    log.error("An exception occurred", ex);
    String message = ex.getMessage() == null ? ex.toString() : ex + " " + ex.getMessage();
    message = message + "\n" + Arrays.toString(ex.getStackTrace());
    message = message.length() > 1000 ? message.substring(0, 1000) : message;
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(message));
  }
}
