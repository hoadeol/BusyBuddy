package com.hoadeol.busybuddy.service;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.CategoryException;
import com.hoadeol.busybuddy.exception.MemberException;
import com.hoadeol.busybuddy.exception.TaskException;
import com.hoadeol.busybuddy.mapper.TaskMapper;
import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.CompletionDetails;
import com.hoadeol.busybuddy.model.Priority;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.CategoryRepository;
import com.hoadeol.busybuddy.repository.MemberRepository;
import com.hoadeol.busybuddy.repository.TaskRepository;
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
    List<Task> tasks = taskRepository.findAll();
    return TaskMapper.INSTANCE.toDTOList(tasks);
  }

  // Task ID로 조회
  public TaskDTO getTaskById(Long taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> TaskException.notFound(taskId));
    return TaskMapper.INSTANCE.toDTO(task);
  }

  // Member ID로 해당 Member의 Task 목록 조회
  public List<TaskDTO> getTasksByMemberId(Long memberId) {
    if (!memberRepository.existsById(memberId)) {
      throw MemberException.notFound(memberId);
    }

    List<Task> tasks = taskRepository.findByMemberId(memberId);
    return TaskMapper.INSTANCE.toDTOList(tasks);
  }

  // Category ID로 해당 Category의 Task 목록 조회
  public List<TaskDTO> getTasksByCategoryId(Long categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw CategoryException.notFound(categoryId);
    }

    List<Task> tasks = taskRepository.findByCategoryId(categoryId);
    return TaskMapper.INSTANCE.toDTOList(tasks);
  }

  // Task 저장
  @Transactional
  public TaskDTO saveTask(TaskDTO taskDTO) {
    Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
    Task savedTask = taskRepository.save(task);
    return TaskMapper.INSTANCE.toDTO(savedTask);
  }

  // Task 업데이트
  @Transactional
  public TaskDTO updateTask(Long taskId, TaskDTO updatedTaskDTO) {
    Task existingTask = taskRepository.findById(taskId)
        .orElseThrow(() -> TaskException.notFound(taskId));

    Category category = getCategoryById(updatedTaskDTO.getCategoryId());

    Priority priority = Optional.ofNullable(updatedTaskDTO.getPriority())
        .map(Priority::fromString)
        .orElse(null);

    CompletionDetails completionDetails = CompletionDetails.builder()
        .isCompleted(updatedTaskDTO.getIsCompleted())
        .completeDate(updatedTaskDTO.getCompleteDate())
        .build();

    existingTask.update(category, updatedTaskDTO.getTitle(), updatedTaskDTO.getContent(),
        updatedTaskDTO.getDueDate(), priority, completionDetails);

    return TaskMapper.INSTANCE.toDTO(existingTask);
  }

  // Task 삭제
  @Transactional
  public void deleteTask(Long taskId) {
    if (!taskRepository.existsById(taskId)) {
      throw TaskException.notFound(taskId);
    }

    taskRepository.deleteById(taskId);
  }

  private Category getCategoryById(Long categoryId) {
    return categoryId != null ? categoryRepository.findById(categoryId)
        .orElseThrow(() -> CategoryException.notFound(categoryId)) : null;
  }
}
