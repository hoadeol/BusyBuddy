package com.hoadeol.busybuddy.exception;

import com.hoadeol.busybuddy.constants.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    FieldError fieldError = bindingResult.getFieldError();
    String errorMessage =
        (fieldError != null) ? fieldError.getDefaultMessage() : "Validation failed";

    return ResponseEntity.badRequest().body(errorMessage);
  }

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<String> handleCustomException(CustomException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(errorCode.getMessage());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<String> handleServerException() {
    return ResponseEntity
        .internalServerError()
        .body(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
  }
}
