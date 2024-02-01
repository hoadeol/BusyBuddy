package com.hoadeol.busybuddy.exception;

import com.hoadeol.busybuddy.constants.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(Long id, ErrorCode errorCode) {
    super(errorCode.getLog() + id);
    this.errorCode = errorCode;
  }

  public CustomException(String message, ErrorCode errorCode) {
    super(errorCode.getLog() + message);
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getLog());
    this.errorCode = errorCode;
  }

}