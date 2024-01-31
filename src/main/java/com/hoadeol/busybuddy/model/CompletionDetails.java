package com.hoadeol.busybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletionDetails {

  @Column(name = "COMPLETE_YN")
  @Builder.Default
  private Boolean isCompleted = false;

  @Column(name = "COMPLETE_DT")
  private LocalDateTime completeDate;
}