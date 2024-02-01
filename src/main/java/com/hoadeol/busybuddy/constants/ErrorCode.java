package com.hoadeol.busybuddy.constants;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  //400 BAD_REQUEST 잘못된 요청
  INVALID_PARAMETER(BAD_REQUEST, "파라미터 값을 확인해주세요."),

  //404 NOT_FOUND 잘못된 리소스 접근
  CATEGORY_NOT_FOUND(NOT_FOUND, "존재하지 않는 카테고리 ID 입니다."),
  TASK_NOT_FOUND(NOT_FOUND, "존재하지 않는 할 일 ID 입니다."),
  MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 회원 ID 입니다."),

  SAVED_CATEGORY_NOT_FOUND(NOT_FOUND, "저장하지 않은 카테고리입니다."),
  SAVED_TASK_NOT_FOUND(NOT_FOUND, "저장하지 않은 할 일입니다."),
  SAVED_MEMBER_NOT_FOUND(NOT_FOUND, "저장하지 않은 회원입니다."),

  //409 CONFLICT 중복된 리소스
  ALREADY_SAVED_CATEGORY(CONFLICT, "이미 저장한 카테고리입니다."),
  ALREADY_SAVED_TASK(CONFLICT, "이미 저장한 할 일입니다."),
  ALREADY_SAVED_MEMBER(CONFLICT, "이미 저장한 회원입니다."),

  //500 INTERNAL SERVER ERROR
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus status;
  private final String message;
}