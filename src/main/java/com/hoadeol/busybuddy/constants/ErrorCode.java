package com.hoadeol.busybuddy.constants;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  //400 BAD_REQUEST 잘못된 요청
  INVALID_PARAMETER(BAD_REQUEST, "파라미터 값을 확인해주세요.", "Invalid parameter: "),

  //404 NOT_FOUND 잘못된 리소스 접근
  CATEGORY_NOT_FOUND(NOT_FOUND, "존재하지 않는 카테고리 ID 입니다.", "Category not found with ID: "),
  TASK_NOT_FOUND(NOT_FOUND, "존재하지 않는 할 일 ID 입니다.", "Task not found with ID: "),
  MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 회원 ID 입니다.", "Member not found with ID: "),

  //500 INTERNAL SERVER ERROR
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
      "서버 에러입니다. 서버 팀에 연락주세요!", "Internal server error");

  private final HttpStatus status;
  private final String message;
  private final String log;
}