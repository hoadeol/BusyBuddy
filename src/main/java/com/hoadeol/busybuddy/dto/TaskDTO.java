package com.hoadeol.busybuddy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Valid
public class TaskDTO {

  @NotNull
  private Long id;

  @NotBlank
  private String memberId;

  private Long categoryId;

  @NotBlank
  private String title;

  private String content;

  @Future(message = "마감일은 현재 시간 이후여야 합니다.")
  private LocalDateTime dueDate;

  private String priority;
  private Boolean isCompleted;
  private LocalDateTime completeDate;
  private LocalDateTime registrationDate;
}
