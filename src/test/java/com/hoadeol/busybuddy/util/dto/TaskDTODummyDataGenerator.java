package com.hoadeol.busybuddy.util.dto;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.util.TestDummyDataGenerator;
import java.time.LocalDate;
import java.util.Optional;

public class TaskDTODummyDataGenerator extends TestDummyDataGenerator {

  public TaskDTO createSavedTaskDTO(Long memberId, Long categoryId, Boolean isTodayTask) {
    //pathVariable value
    Long id = getRandomId(1, NUM_TASKS);

    //not null value
    memberId = Optional.ofNullable(memberId).orElse(getRandomId(1, NUM_MEMBERS));

    //nullable values
    categoryId = Optional.ofNullable(categoryId)
        .orElse(RANDOM.nextBoolean() ? getRandomId(1, NUM_CATEGORIES) : null);
    String content = RANDOM.nextBoolean() ? generateRandomString(STRING_LENGTH * 5) : null;
    String priority = RANDOM.nextBoolean() ? String.valueOf(generateRandomPriority()) : null;
    LocalDate startDate = Boolean.TRUE.equals(isTodayTask) || RANDOM.nextBoolean() ?
        LocalDate.from(generateRandomFuture(DAYS)) : null;
    LocalDate dueDate = RANDOM.nextBoolean() ? LocalDate.from(generateRandomFuture(DAYS)) : null;

    return TaskDTO.builder()
        .id(id)
        .memberId(memberId)
        .categoryId(categoryId)
        .title(generateRandomString(STRING_LENGTH))
        .content(content)
        .priority(priority)
        .startDate(startDate)
        .dueDate(dueDate)
        .build();
  }

  public TaskDTO createSavedTaskDTO() {
    return createSavedTaskDTO(null, null, null);
  }

}
