package com.hoadeol.busybuddy.util.entity;

import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Member.MemberBuilder;
import com.hoadeol.busybuddy.util.TestDummyDataGenerator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberDummyDataGenerator extends TestDummyDataGenerator {

  public List<Member> createMembers() {
    List<Member> members = new ArrayList<>();
    for (int i = 0; i < NUM_MEMBERS; i++) {
      Member member = createMember((long) (i + 1));
      members.add(member);
    }
    return members;
  }

  public Member createMember(Long id) {
    //nullable value
    String email = RANDOM.nextBoolean() ? generateRandomEmail() : null;

    MemberBuilder memberBuilder = Member.builder()
        .id(id)
        .account(generateRandomString(STRING_LENGTH * 3))
        .name(generateRandomString(STRING_LENGTH))
        .email(email)
        .password(generateRandomPassword());

    boolean isNewMember = RANDOM.nextBoolean();
    if (!isNewMember) {
      LocalDateTime registrationDate = generateRandomPast(DAYS);
      LocalDateTime lastLoginDate = generateRandomPast(DAYS / 2);

      memberBuilder
          .registrationDate(registrationDate)
          .lastLoginDate(lastLoginDate);
    }

    return memberBuilder.build();
  }

  public Member createMember() {
    return createMember(getRandomId(1, NUM_MEMBERS));
  }

}
