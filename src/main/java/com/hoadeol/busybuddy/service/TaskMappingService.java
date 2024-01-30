package com.hoadeol.busybuddy.service;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.CategoryNotFoundException;
import com.hoadeol.busybuddy.exception.MemberNotFoundException;
import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Priority;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.CategoryRepository;
import com.hoadeol.busybuddy.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskMappingService {

  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;

  // DTO to Entity 매핑
  public Task mapDTOToEntity(TaskDTO taskDTO) {
    Member member = findMemberById(taskDTO.getMemberId());
    Category category = findCategoryById(taskDTO.getCategoryId());
    Priority priority = mapPriority(taskDTO.getPriority());

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
  public void mapDTOToEntity(TaskDTO taskDTO, Task task) {
    Member member = findMemberById(taskDTO.getMemberId());
    Category category = findCategoryById(taskDTO.getCategoryId());
    Priority priority = mapPriority(taskDTO.getPriority());

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
  public TaskDTO mapEntityToDTO(Task task) {
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
  public List<TaskDTO> mapEntityListToDTOList(List<Task> taskList) {
    return taskList.stream()
        .map(this::mapEntityToDTO)
        .toList();
  }

  // 매핑 관련 유틸리티 메서드
  private Member findMemberById(String memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(memberId));
  }

  private Category findCategoryById(Long categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
  }

  private Priority mapPriority(String priority) {
    return Priority.valueOf(priority);
  }

}