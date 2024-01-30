package com.hoadeol.busybuddy.exception;

public class TaskException extends RuntimeException {

  public TaskException(String message) {
    super(message);
  }

  public static TaskException notFound(Long taskId) {
    return new TaskException("Task not found with ID: " + taskId);
  }

  public static TaskException idMismatch(Long taskId) {
    return new TaskException(
        "Task ID in the request body does not match the provided ID: " + taskId);
  }
}
