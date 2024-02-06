package com.hoadeol.busybuddy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Valid
public class TaskDTO implements Cloneable {

  private Long id;

  private Long memberId;

  private Long categoryId;

  @NotBlank
  private String title;

  private String content;

  @FutureOrPresent(message = "시작일은 과거로 설정할 수 없습니다.")
  private LocalDate startDate;

  @FutureOrPresent(message = "마감일은 과거로 설정할 수 없습니다.")
  private LocalDate dueDate;

  private String priority;
  private Boolean isCompleted;
  private LocalDateTime completeDate;
  private LocalDateTime registrationDate;
  private LocalDateTime lastModifiedDate;

  public void setTodayTask(Boolean isTodayTask) {
    if (Boolean.TRUE.equals(isTodayTask)) {
      this.setStartDate(LocalDate.now());
    }
  }

  @Override
  public TaskDTO clone() throws CloneNotSupportedException {
    return (TaskDTO) super.clone();
  }
}
