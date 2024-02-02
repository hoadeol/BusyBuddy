package com.hoadeol.busybuddy.util;

import com.hoadeol.busybuddy.model.CompletionDetails;
import com.hoadeol.busybuddy.model.Priority;
import java.time.LocalDateTime;
import java.util.Random;

public abstract class TestDummyDataGenerator {

  protected static final Random RANDOM = new Random();
  protected static final int NUM_CATEGORIES = 10;
  protected static final int NUM_MEMBERS = 5;
  protected static final int NUM_TASKS = 20;
  protected static final int STRING_LENGTH = 10;
  protected static final int EMAIL_LENGTH = 8;
  protected static final int DAYS = 30;
  protected static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  protected static final String EMAIL_DOMAIN = "@example.com";

  protected Priority generateRandomPriority() {
    return Priority.values()[RANDOM.nextInt(Priority.values().length)];
  }

  protected CompletionDetails generateCompletionDetails() {
    boolean isCompleted = RANDOM.nextBoolean();
    LocalDateTime completeDate = generateRandomFuture(DAYS);
    return new CompletionDetails(isCompleted, completeDate);
  }

  protected LocalDateTime generateRandomFuture(int plusDays) {
    return LocalDateTime.now().plusSeconds(1).plusDays(RANDOM.nextInt(plusDays));
  }

  protected LocalDateTime generateRandomPast(int minusDays) {
    return LocalDateTime.now().plusSeconds(1).minusDays(RANDOM.nextInt(minusDays));
  }

  protected String generateRandomString(int length) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < length; i++) {
      result.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }

    return result.toString();
  }

  protected String generateRandomEmail() {
    return generateRandomString(EMAIL_LENGTH) + EMAIL_DOMAIN;
  }

  protected String generateRandomPassword() {
    String specialCharacters = "!@#$%^&*()-_=+";

    StringBuilder password = new StringBuilder();
    password.append(generateRandomString(STRING_LENGTH));

    // Add at least one special character
    password.append(specialCharacters.charAt(RANDOM.nextInt(specialCharacters.length())));

    return password.toString();
  }

  protected Long getRandomId(int min, int max) {
    return (long) (min + RANDOM.nextInt(max - min + 1));
  }

  protected int getRandomIndex(int min, int max) {
    return min + RANDOM.nextInt(max - min);
  }
}
