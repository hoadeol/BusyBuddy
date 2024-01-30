package com.hoadeol.busybuddy.service;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.TaskNotFoundException;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMappingService taskMappingService;

  // 모든 Task 조회
  public List<TaskDTO> getAllTasks() {
    List<Task> tasks = taskRepository.findAll();
    return taskMappingService.mapEntityListToDTOList(tasks);
  }

  // Task ID로 조회
  public Optional<TaskDTO> getTaskById(Long taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    return task.map(taskMappingService::mapEntityToDTO);
  }

  // Member ID로 해당 Member의 Task 목록 조회
  public List<TaskDTO> getTasksByMemberId(String memberId) {
    List<Task> tasks = taskRepository.findByMemberId(memberId);
    return taskMappingService.mapEntityListToDTOList(tasks);
  }

  // Category ID로 해당 Category의 Task 목록 조회
  public List<TaskDTO> getTasksByCategoryId(Long categoryId) {
    List<Task> tasks = taskRepository.findByCategoryId(categoryId);
    return taskMappingService.mapEntityListToDTOList(tasks);
  }

  // Task 저장
  @Transactional
  public TaskDTO saveTask(TaskDTO taskDTO) {
    Task task = taskMappingService.mapDTOToEntity(taskDTO);
    Task savedTask = taskRepository.save(task);
    return taskMappingService.mapEntityToDTO(savedTask);
  }

  // Task 업데이트
  @Transactional
  public TaskDTO updateTask(Long taskId, TaskDTO updatedTaskDTO) {
    Task existingTask = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException(taskId));
    taskMappingService.mapDTOToEntity(updatedTaskDTO, existingTask);

    Task updatedTask = taskRepository.save(existingTask);
    return taskMappingService.mapEntityToDTO(updatedTask);
  }

  // Task 삭제
  @Transactional
  public void deleteTask(Long taskId) {
    taskRepository.deleteById(taskId);
  }

}
