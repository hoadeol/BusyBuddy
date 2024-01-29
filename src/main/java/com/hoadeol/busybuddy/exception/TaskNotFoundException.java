package com.hoadeol.busybuddy.exception;

public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(Long taskId) {
    super("Task not found with ID: " + taskId);
  }
}
