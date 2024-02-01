package com.hoadeol.busybuddy.exception;

import com.hoadeol.busybuddy.constants.ErrorCode;

public class TaskException extends CustomException {

  public TaskException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public static TaskException notFound(Long taskId) {
    return new TaskException("Task not found with ID: " + taskId,
        ErrorCode.TASK_NOT_FOUND);
  }
}
