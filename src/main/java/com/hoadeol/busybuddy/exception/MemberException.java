package com.hoadeol.busybuddy.exception;

public class MemberException extends RuntimeException {

  public MemberException(String message) {
    super(message);
  }

  public static MemberException notFound(Long memberId) {
    return new MemberException("Member not found with ID: " + memberId);
  }
}
