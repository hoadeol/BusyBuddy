package com.hoadeol.busybuddy.exception;

public class CategoryException extends RuntimeException {

  public CategoryException(String message) {
    super(message);
  }

  public static CategoryException notFound(Long categoryId) {
    return new CategoryException("Category not found with ID: " + categoryId);
  }
}
