package com.hoadeol.busybuddy.controller;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tasks")
@Validated
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private static final String X_TOTAL_COUNT = "X-Total-Count";

  @GetMapping
  public ResponseEntity<List<TaskDTO>> getAllTasks() {
    log.info("Received request to get all tasks.");
    List<TaskDTO> allTasks = taskService.getAllTasks();
    log.info("Retrieved {} tasks.", allTasks.size());
    return ResponseEntity.ok()
        .header(X_TOTAL_COUNT, String.valueOf(allTasks.size()))
        .body(allTasks);
  }

  @GetMapping("/{taskId}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
    TaskDTO task = taskService.getTaskById(taskId);
    return ResponseEntity.ok(task);
  }

  @GetMapping("/member/{memberId}")
  public ResponseEntity<List<TaskDTO>> getTasksByMemberId(@PathVariable Long memberId) {
    List<TaskDTO> tasks = taskService.getTasksByMemberId(memberId);
    return ResponseEntity.ok()
        .header(X_TOTAL_COUNT, String.valueOf(tasks.size()))
        .body(tasks);
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<TaskDTO>> getTasksByCategoryId(@PathVariable Long categoryId) {
    List<TaskDTO> tasks = taskService.getTasksByCategoryId(categoryId);
    return ResponseEntity.ok()
        .header(X_TOTAL_COUNT, String.valueOf(tasks.size()))
        .body(tasks);
  }

  @PostMapping
  public ResponseEntity<TaskDTO> saveTask(@Valid @RequestBody TaskDTO taskDTO) {
    TaskDTO savedTask = taskService.saveTask(taskDTO);
    return ResponseEntity.ok(savedTask);
  }

  @PutMapping("/{taskId}")
  public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId,
      @Valid @RequestBody TaskDTO updatedTaskDTO) {
    TaskDTO updatedTask = taskService.updateTask(taskId, updatedTaskDTO);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
    taskService.deleteTask(taskId);
    return ResponseEntity.noContent().build();
  }
}
