package com.hoadeol.busybuddy.exception;

import com.hoadeol.busybuddy.constants.ErrorCode;

public class CategoryException extends CustomException {

  public CategoryException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public static CategoryException notFound(Long categoryId) {
    return new CategoryException("Category not found with ID: " + categoryId,
        ErrorCode.CATEGORY_NOT_FOUND);
  }
}
