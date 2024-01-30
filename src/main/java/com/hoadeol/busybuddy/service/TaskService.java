package com.hoadeol.busybuddy.service;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.CategoryNotFoundException;
import com.hoadeol.busybuddy.exception.MemberNotFoundException;
import com.hoadeol.busybuddy.exception.TaskNotFoundException;
import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Priority;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.CategoryRepository;
import com.hoadeol.busybuddy.repository.MemberRepository;
import com.hoadeol.busybuddy.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    return mapEntityListToDTOList(tasks);
  }

  // Task ID로 조회
  public Optional<TaskDTO> getTaskById(Long taskId) {
    return taskRepository.findById(taskId)
        .map(this::mapEntityToDTO);
  }

  // Member ID로 해당 Member의 Task 목록 조회
  public List<TaskDTO> getTasksByMemberId(String memberId) {
    List<Task> tasks = taskRepository.findByMemberId(memberId);
    return mapEntityListToDTOList(tasks);
  }

  // Category ID로 해당 Category의 Task 목록 조회
  public List<TaskDTO> getTasksByCategoryId(Long categoryId) {
    List<Task> tasks = taskRepository.findByCategoryId(categoryId);
    return mapEntityListToDTOList(tasks);
  }

  // Task 저장
  @Transactional
  public TaskDTO saveTask(TaskDTO taskDTO) {
    Task task = mapDTOToEntity(taskDTO);
    Task savedTask = taskRepository.save(task);
    return mapEntityToDTO(savedTask);
  }

  // Task 업데이트
  @Transactional
  public TaskDTO updateTask(Long taskId, TaskDTO updatedTaskDTO) {
    Task existingTask = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException(taskId));
    mapDTOToEntity(updatedTaskDTO, existingTask);

    Task updatedTask = taskRepository.save(existingTask);
    return mapEntityToDTO(updatedTask);
  }

  // Task 삭제
  @Transactional
  public void deleteTask(Long taskId) {
    taskRepository.deleteById(taskId);
  }

  // DTO to Entity 매핑
  private Task mapDTOToEntity(TaskDTO taskDTO) {
    String memberId = taskDTO.getMemberId();
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(memberId));

    Long categoryId = taskDTO.getCategoryId();
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Priority priority = Priority.valueOf(taskDTO.getPriority());

    return Task.builder()
        .id(taskDTO.getId())
        .member(member)
        .category(category)
        .title(taskDTO.getTitle())
        .content(taskDTO.getContent())
        .dueDate(taskDTO.getDueDate())
        .priority(priority)
        .isCompleted(taskDTO.getIsCompleted())
        .completeDate(taskDTO.getCompleteDate())
        .registrationDate(taskDTO.getRegistrationDate())
        .build();
  }

  // DTO to Entity 매핑 (업데이트 시에 기존 Entity에 매핑)
  private void mapDTOToEntity(TaskDTO taskDTO, Task task) {
    String memberId = taskDTO.getMemberId();
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(memberId));

    Long categoryId = taskDTO.getCategoryId();
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Priority priority = Priority.valueOf(taskDTO.getPriority());

    task.setMember(member);
    task.setCategory(category);
    task.setTitle(taskDTO.getTitle());
    task.setContent(taskDTO.getContent());
    task.setDueDate(taskDTO.getDueDate());
    task.setPriority(priority);
    task.setIsCompleted(taskDTO.getIsCompleted());
    task.setCompleteDate(taskDTO.getCompleteDate());
    task.setRegistrationDate(taskDTO.getRegistrationDate());
  }

  // Entity to DTO 매핑
  private TaskDTO mapEntityToDTO(Task task) {
    return TaskDTO.builder()
        .id(task.getId())
        .memberId(task.getMember().getId())
        .categoryId(task.getCategory().getId())
        .title(task.getTitle())
        .content(task.getContent())
        .dueDate(task.getDueDate())
        .priority(task.getPriority().name())
        .isCompleted(task.getIsCompleted())
        .completeDate(task.getCompleteDate())
        .registrationDate(task.getRegistrationDate())
        .build();
  }

  // Entity List to DTO List 매핑
  private List<TaskDTO> mapEntityListToDTOList(List<Task> taskList) {
    return taskList.stream()
        .map(this::mapEntityToDTO)
        .collect(Collectors.toList());
  }

}
