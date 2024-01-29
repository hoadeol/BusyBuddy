package com.hoadeol.busybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"member", "category"})
public class Task {

  @GeneratedValue
  @Id
  @Column(name = "TASK_ID")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID", nullable = false)
  private Member member;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATE_ID")
  private Category category;
  @Column(length = 50)
  @Size(max = 50)
  private String title;
  @Column(length = 500)
  @Size(max = 500)
  private String content;
  @Column(name = "DUE_DT")
  private LocalDateTime dueDate = LocalDateTime.now().toLocalDate().plusDays(1).atStartOfDay();
  private Integer priority;
  @Column(name = "COMPLETE_YN")
  private Boolean isCompleted = false;
  @Column(name = "COMPLETE_DT")
  private LocalDateTime completeDate;
  @Column(name = "REG_DT")
  private LocalDateTime registrationDate = LocalDateTime.now();
}
