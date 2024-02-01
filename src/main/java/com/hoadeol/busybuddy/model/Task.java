package com.hoadeol.busybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = {"member", "category"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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
  @Size(min = 1, max = 50)
  @NotBlank
  private String title;

  @Column(length = 500)
  @Size(max = 500)
  private String content;

  @Future(message = "마감일은 현재 시간 이후여야 합니다.")
  @Column(name = "DUE_DT")
  @Builder.Default
  private LocalDateTime dueDate = LocalDateTime.now().with(LocalTime.MAX);

  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  @Builder.Default
  private Priority priority = Priority.NORMAL;

  @Embedded
  private CompletionDetails completionDetails;

  @Column(name = "REG_DT")
  @Builder.Default
  private LocalDateTime registrationDate = LocalDateTime.now();

  @Column(name = "MOD_DT")
  private LocalDateTime lastModifiedDate;

  @Builder
  public Task(Long id, Member member, Category category, String title, String content,
      LocalDateTime dueDate, Priority priority, CompletionDetails completionDetails,
      LocalDateTime registrationDate, LocalDateTime lastModifiedDate) {
    this.id = id;
    this.member = member;
    this.category = category;
    this.title = title;
    this.content = content;
    this.dueDate = dueDate;
    this.priority = priority;
    this.completionDetails = completionDetails;
    this.registrationDate = registrationDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  public Task update(Category category, String title, String content, LocalDateTime dueDate,
      Priority priority, CompletionDetails completionDetails) {
    this.category = category;
    this.title = title;
    this.content = content;
    this.dueDate = dueDate;
    this.priority = priority;
    this.completionDetails = completionDetails;
    this.lastModifiedDate = LocalDateTime.now();
    return this;
  }
}
