package com.hoadeol.busybuddy.util.entity;

import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.util.TestDummyDataGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDummyDataGenerator extends TestDummyDataGenerator {

  private final MemberDummyDataGenerator memberDataGenerator = new MemberDummyDataGenerator();

  public List<Category> createCategories() {
    List<Category> categories = new ArrayList<>();
    List<Member> members = memberDataGenerator.createMembers();

    for (int i = 0; i < NUM_CATEGORIES; i++) {
      Member member = members.get(getRandomIndex(0, NUM_MEMBERS));
      Category category = createCategory((long) (i + 1), member);
      categories.add(category);
    }
    return categories;
  }

  public Category createCategory(Long id, Member member) {
    //not null values
    id = Optional.ofNullable(id).orElse(getRandomId(1, NUM_CATEGORIES));
    member = Optional.ofNullable(member).orElse(memberDataGenerator.createMember());

    return Category.builder()
        .id(id)
        .member(member)
        .name(generateRandomString(STRING_LENGTH))
        .build();
  }

  public Category createCategory() {
    return createCategory(null, null);
  }

  public Category createCategoryWithMember(Member member) {
    return createCategory(null, member);
  }
}
