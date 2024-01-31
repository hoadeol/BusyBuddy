package com.hoadeol.busybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  @Column(unique = true, nullable = false, length = 30)
  @Size(max = 30)
  private String account;

  @Column(name = "MEMBER_NAME", nullable = false, length = 30)
  @Size(max = 30)
  private String name;

  @Column(unique = true, length = 50)
  @Email
  private String email;

  @Column(nullable = false, length = 100)
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*()-_=+]).{8,}$",
      message = "비밀번호는 8문자 이상이어야 하며, 특수문자 1개와 영문자 1개가 포함되어야 합니다.")
  @Size(max = 100)
  @NotBlank
  private String password;

  @Column(name = "REG_DT")
  @Builder.Default
  private LocalDateTime registrationDate = LocalDateTime.now();

  @Column(name = "LAST_LOGIN_DT")
  @Builder.Default
  private LocalDateTime lastLoginDate = LocalDateTime.now();

  @Builder
  public Member(Long id, String account, String name, String email, String password,
      LocalDateTime registrationDate, LocalDateTime lastLoginDate) {
    this.id = id;
    this.account = account;
    this.name = name;
    this.email = email;
    this.password = password;
    this.registrationDate = registrationDate;
    this.lastLoginDate = lastLoginDate;
  }

}
