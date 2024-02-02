package com.hoadeol.busybuddy.util.entity;

import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.model.Task.TaskBuilder;
import com.hoadeol.busybuddy.util.TestDummyDataGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDummyDataGenerator extends TestDummyDataGenerator {

  private final CategoryDummyDataGenerator categoryDataGenerator = new CategoryDummyDataGenerator();
  private final MemberDummyDataGenerator memberDataGenerator = new MemberDummyDataGenerator();

  public List<Task> createTasks(List<Member> members, List<Category> categories) {
    List<Task> tasks = new ArrayList<>();
    members = Optional.ofNullable(members).orElse(memberDataGenerator.createMembers());
    categories = Optional.ofNullable(categories).orElse(categoryDataGenerator.createCategories());

    for (int i = 0; i < NUM_TASKS; i++) {
      Member member = members.get(getRandomIndex(0, NUM_MEMBERS));
      Category category = categories.get(getRandomIndex(0, NUM_CATEGORIES));

      Task task = createTask(i + 1, member, category);
      tasks.add(task);
    }
    return tasks;
  }

  public List<Task> createTasks() {
    return createTasks(null, null);
  }

  public List<Task> createTasksWithMemberAndCategory(Member member, Category category) {
    List<Task> tasks = new ArrayList<>();
    member = Optional.ofNullable(member).orElse(memberDataGenerator.createMember());
    category = Optional.ofNullable(category).orElse(categoryDataGenerator.createCategory());

    for (int i = 0; i < NUM_TASKS; i++) {
      Task task = createTask(i + 1, member, category);
      tasks.add(task);
    }
    return tasks;
  }

  public List<Task> createTasksWithMember(Member member) {
    return createTasksWithMemberAndCategory(member, null);
  }

  public List<Task> createTasksWithCategory(Category category) {
    return createTasksWithMemberAndCategory(null, category);
  }


  public Task createTask(long id, Member member, Category category) {
    member = Optional.ofNullable(member).orElse(memberDataGenerator.createMember());
    category = Optional.ofNullable(category).orElse(categoryDataGenerator.createCategory());

    boolean isNewTask = RANDOM.nextBoolean();
    LocalDate startDate = RANDOM.nextBoolean() ? LocalDate.from(generateRandomFuture(DAYS)) : null;
    LocalDate dueDate = RANDOM.nextBoolean() ? LocalDate.from(generateRandomFuture(DAYS)) : null;

    TaskBuilder taskBuilder = Task.builder()
        .id(id)
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

    return taskBuilder.build();
  }

  public Task createTask() {
    return createTask(RANDOM.nextLong(), null, null);
  }

}
