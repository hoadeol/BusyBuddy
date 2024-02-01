package com.hoadeol.busybuddy.service;

import static com.hoadeol.busybuddy.constants.ErrorCode.CATEGORY_NOT_FOUND;
import static com.hoadeol.busybuddy.constants.ErrorCode.MEMBER_NOT_FOUND;
import static com.hoadeol.busybuddy.constants.ErrorCode.TASK_NOT_FOUND;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.CustomException;
import com.hoadeol.busybuddy.mapper.TaskMapper;
import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.CompletionDetails;
import com.hoadeol.busybuddy.model.Priority;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.CategoryRepository;
import com.hoadeol.busybuddy.repository.MemberRepository;
import com.hoadeol.busybuddy.repository.TaskRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;

  // 모든 Task 조회
  public List<TaskDTO> getAllTasks() {
    log.info("Fetching all tasks");
    List<Task> tasks = taskRepository.findAll();
    log.info("Fetched {} tasks", tasks.size());
    return TaskMapper.INSTANCE.toDTOList(tasks);
  }

  // Task ID로 조회
  public TaskDTO getTaskById(Long taskId) {
    log.info("Fetching task by ID: {}", taskId);
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> TaskException.notFound(taskId));
    return TaskMapper.INSTANCE.toDTO(task);
  }

  // Member ID로 해당 Member의 Task 목록 조회
  public List<TaskDTO> getTasksByMemberId(Long memberId) {
    log.info("Fetching tasks by Member ID: {}", memberId);
    if (!memberRepository.existsById(memberId)) {
      throw MemberException.notFound(memberId);
    }

    List<Task> tasks = taskRepository.findByMemberId(memberId);
    log.info("Fetched {} tasks by Member ID: {}", tasks.size(), memberId);
    return TaskMapper.INSTANCE.toDTOList(tasks);
  }

  // Category ID로 해당 Category의 Task 목록 조회
  public List<TaskDTO> getTasksByCategoryId(Long categoryId) {
    log.info("Fetching tasks by Category ID: {}", categoryId);
    if (!categoryRepository.existsById(categoryId)) {
      throw CategoryException.notFound(categoryId);
    }

    List<Task> tasks = taskRepository.findByCategoryId(categoryId);
    log.info("Fetched {} tasks by Category ID: {}", tasks.size(), categoryId);
    return TaskMapper.INSTANCE.toDTOList(tasks);
  }

  // Task 저장
  @Transactional
  public TaskDTO saveTask(TaskDTO taskDTO) {
    log.info("Saving new task");
    Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
    Task savedTask = taskRepository.save(task);
    log.info("Task saved successfully. Task ID: {}", savedTask.getId());
    return TaskMapper.INSTANCE.toDTO(savedTask);
  }

  // Task 업데이트
  @Transactional
  public TaskDTO updateTask(Long taskId, TaskDTO updatedTaskDTO) {
    log.info("Updating task with ID: {}", taskId);

    Task existingTask = taskRepository.findById(taskId)
        .orElseThrow(() -> TaskException.notFound(taskId));

    Category category = getCategoryById(updatedTaskDTO.getCategoryId());
    log.debug("Category: {}", category);

    LocalDateTime dueDate = Optional.ofNullable(updatedTaskDTO.getDueDate())
        .orElse(LocalDateTime.now().with(LocalTime.MAX));
    log.debug("Due Date: {}", dueDate);

    Priority priority = Optional.ofNullable(updatedTaskDTO.getPriority())
        .map(Priority::fromString)
        .orElse(null);
    log.debug("Priority: {}", priority);

    CompletionDetails completionDetails = CompletionDetails.builder()
        .isCompleted(updatedTaskDTO.getIsCompleted())
        .completeDate(updatedTaskDTO.getCompleteDate())
        .build();
    log.debug("Completion Details: {}", completionDetails);

    existingTask.update(category, updatedTaskDTO.getTitle(), updatedTaskDTO.getContent(),
        dueDate, priority, completionDetails);

    log.info("Task updated successfully. Task ID: {}", taskId);
    return TaskMapper.INSTANCE.toDTO(existingTask);
  }

  // Task 삭제
  @Transactional
  public void deleteTask(Long taskId) {
    if (!taskRepository.existsById(taskId)) {
      throw TaskException.notFound(taskId);
    }

    taskRepository.deleteById(taskId);
    log.info("Task deleted successfully. Task ID: {}", taskId);
  }

  private Category getCategoryById(Long categoryId) {
    return categoryId != null ? categoryRepository.findById(categoryId)
        .orElseThrow(() -> CategoryException.notFound(categoryId)) : null;
  }
}
