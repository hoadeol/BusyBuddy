package com.hoadeol.busybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@ToString
public class Member {

  @GeneratedValue
  @Id
  @Column(name = "MEMBER_ID", length = 30)
  @Size(max = 30)
  private String id;
  @Column(nullable = false, length = 30)
  @Size(max = 30)
  private String memberName;
  @Column(unique = true, length = 50)
  @Email
  private String email;
  @Column(nullable = false, length = 100)
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*()-_=+]).{8,}$",
      message = "비밀번호는 8문자 이상이어야 하며, 특수문자 1개와 영문자 1개가 포함되어야 합니다.")
  private String password;
  @Column(name = "REG_DT")
  private LocalDateTime registrationDate = LocalDateTime.now();
  @Column(name = "LAST_LOGIN_DT")
  private LocalDateTime lastLoginDate = LocalDateTime.now();

}
