package com.hoadeol.busybuddy.exception;

import com.hoadeol.busybuddy.constants.ErrorCode;

public class MemberException extends CustomException {

  public MemberException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public static MemberException notFound(Long memberId) {
    return new MemberException("Member not found with ID: " + memberId,
        ErrorCode.MEMBER_NOT_FOUND);
  }
}
