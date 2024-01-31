package com.hoadeol.busybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CompletionDetails {

  @Column(name = "COMPLETE_YN")
  @Builder.Default
  private Boolean isCompleted = false;

  @Column(name = "COMPLETE_DT")
  private LocalDateTime completeDate;

  @Builder
  public CompletionDetails(Boolean isCompleted, LocalDateTime completeDate) {
    this.isCompleted = isCompleted;
    this.completeDate = Optional.ofNullable(isCompleted).orElse(false) ? completeDate : null;
  }
}