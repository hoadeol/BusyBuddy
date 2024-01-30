package com.hoadeol.busybuddy.controller;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("/tasks")
@Validated
@AllArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping
  public List<TaskDTO> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/{taskId}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
    return taskService.getTaskById(taskId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/member/{memberId}")
  public List<TaskDTO> getTasksByMemberId(@PathVariable String memberId) {
    return taskService.getTasksByMemberId(memberId);
  }

  @GetMapping("/category/{categoryId}")
  public List<TaskDTO> getTasksByCategoryId(@PathVariable Long categoryId) {
    return taskService.getTasksByCategoryId(categoryId);
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
