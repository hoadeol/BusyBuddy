package com.hoadeol.busybuddy.util;

import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.CompletionDetails;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Member.MemberBuilder;
import com.hoadeol.busybuddy.model.Priority;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.model.Task.TaskBuilder;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyDataGenerator {

  private final EntityManager em;
  private static final Random RANDOM = new Random();
  private static final int NUM_CATEGORIES = 10;
  private static final int NUM_MEMBERS = 5;
  private static final int NUM_TASKS = 20;
  private static final int STRING_LENGTH = 10;
  private static final int EMAIL_LENGTH = 8;
  private static final int DAYS = 30;
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String EMAIL_DOMAIN = "@example.com";

  @Transactional
  public void createDummyData() {
    log.info("Start createDummyData");

    createMembers();
    createCategories();
    createTasks();

    log.info("createDummyData completed");
  }

  private void createMembers() {
    log.info("Start createMembers");

    for (int i = 0; i < NUM_MEMBERS; i++) {
      boolean isNewMember = RANDOM.nextBoolean();
      Member member = createMember(isNewMember);
      em.persist(member);
    }

    log.info("createMembers completed");
  }

  private void createCategories() {
    log.info("Start createCategories");

    for (int i = 0; i < NUM_CATEGORIES; i++) {
      Member member = em.getReference(Member.class, getRandomId(1, NUM_MEMBERS));

      Category category = Category.builder()
          .member(member)
          .name(generateRandomString(STRING_LENGTH))
          .build();

      em.persist(category);
    }

    log.info("createCategories completed");
  }

  private void createTasks() {
    log.info("Start createTasks");

    for (int i = 0; i < NUM_TASKS; i++) {
      boolean isNewTask = RANDOM.nextBoolean();
      Task task = createTask(isNewTask);
      em.persist(task);
    }

    log.info("createTasks completed");
  }

  private Member createMember(boolean isNewMember) {
    MemberBuilder memberBuilder = Member.builder()
        .account(generateRandomString(STRING_LENGTH * 3))
        .name(generateRandomString(STRING_LENGTH))
        .email(generateRandomEmail())
        .password(generateRandomPassword());

    if (!isNewMember) {
      LocalDateTime registrationDate = generateRandomPast(DAYS);
      LocalDateTime lastLoginDate = generateRandomPast(DAYS / 2);

      memberBuilder
          .registrationDate(registrationDate)
          .lastLoginDate(lastLoginDate);
    }

    return memberBuilder.build();
  }

  private Task createTask(boolean isNewTask) {
    Member member = em.getReference(Member.class, getRandomId(1, NUM_MEMBERS));
    Category category = em.getReference(Category.class, getRandomId(1, NUM_CATEGORIES));
    LocalDateTime startDate = RANDOM.nextBoolean() ? generateRandomFuture(DAYS) : null;
    LocalDateTime dueDate = RANDOM.nextBoolean() ? generateRandomFuture(DAYS) : null;

    TaskBuilder taskBuilder = Task.builder()
        .member(member)
        .category(category)
        .priority(generateRandomPriority())
        .title(generateRandomString(STRING_LENGTH))
        .content(generateRandomString(STRING_LENGTH * 5))  // Increased length for content
        .startDate(startDate)
        .dueDate(dueDate);

    if (!isNewTask) {
      LocalDateTime lastModifiedDate = RANDOM.nextBoolean() ? generateRandomPast(DAYS / 2) : null;
      taskBuilder
          .completionDetails(generateCompletionDetails())
          .registrationDate(generateRandomPast(DAYS))
          .lastModifiedDate(lastModifiedDate);
    }

    Task build = taskBuilder.build();
    log.debug("Task: {}", build);

    return build;
  }

  private Priority generateRandomPriority() {
    return Priority.values()[RANDOM.nextInt(Priority.values().length)];
  }

  private CompletionDetails generateCompletionDetails() {
    boolean isCompleted = RANDOM.nextBoolean();
    LocalDateTime completeDate = generateRandomFuture(DAYS);
    return new CompletionDetails(isCompleted, completeDate);
  }

  private LocalDateTime generateRandomFuture(int plusDays) {
    return LocalDateTime.now().plusSeconds(1).plusDays(RANDOM.nextInt(plusDays));
  }

  private LocalDateTime generateRandomPast(int minusDays) {
    return LocalDateTime.now().plusSeconds(1).minusDays(RANDOM.nextInt(minusDays));
  }

  private String generateRandomString(int length) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < length; i++) {
      result.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }

    return result.toString();
  }

  private String generateRandomEmail() {
    return generateRandomString(EMAIL_LENGTH) + EMAIL_DOMAIN;
  }

  private String generateRandomPassword() {
    String specialCharacters = "!@#$%^&*()-_=+";

    StringBuilder password = new StringBuilder();
    password.append(generateRandomString(STRING_LENGTH));

    // Add at least one special character
    password.append(specialCharacters.charAt(RANDOM.nextInt(specialCharacters.length())));

    return password.toString();
  }

  private Long getRandomId(int min, int max) {
    return (long) (min + RANDOM.nextInt(max - min + 1));
  }
}
