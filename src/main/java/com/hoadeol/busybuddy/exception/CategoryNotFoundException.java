package com.hoadeol.busybuddy.exception;

public class CategoryNotFoundException  extends RuntimeException {

  public CategoryNotFoundException(Long categoryId) {
    super("Category not found with ID: " + categoryId);
  }
}
