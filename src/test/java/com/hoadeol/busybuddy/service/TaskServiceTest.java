package com.hoadeol.busybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hoadeol.busybuddy.constants.ErrorCode;
import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.CustomException;
import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.CategoryRepository;
import com.hoadeol.busybuddy.repository.MemberRepository;
import com.hoadeol.busybuddy.repository.TaskRepository;
import com.hoadeol.busybuddy.util.entity.CategoryDummyDataGenerator;
import com.hoadeol.busybuddy.util.entity.MemberDummyDataGenerator;
import com.hoadeol.busybuddy.util.entity.TaskDummyDataGenerator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private TaskService taskService;

  private final CategoryDummyDataGenerator categoryDataGenerator = new CategoryDummyDataGenerator();
  private final MemberDummyDataGenerator memberDataGenerator = new MemberDummyDataGenerator();
  private final TaskDummyDataGenerator taskDataGenerator = new TaskDummyDataGenerator();

  @Test
  void getAllTasks() {
    //given
    List<Task> tasks = taskDataGenerator.createTasks();

    //when
    when(taskRepository.findAll()).thenReturn(tasks);
    List<TaskDTO> result = taskService.getAllTasks();

    //then
    assertEquals(tasks.size(), result.size());
    verify(taskRepository, times(1)).findAll();
  }

  @Test
  void getTaskById() {
    //given
    Task task = taskDataGenerator.createTask();
    Long taskId = task.getId();

    //when
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
    TaskDTO result = taskService.getTaskById(taskId);

    //then
    assertNotNull(result);
    assertEquals(task.getId(), result.getId());
    verify(taskRepository, times(1)).findById(taskId);
  }

  @Test
  void getTaskById_NotFound() {
    //given
    Long taskId = 1L;

    //when
    when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

    //then
    CustomException exception
        = assertThrows(CustomException.class, () -> taskService.getTaskById(taskId));
    assertEquals(ErrorCode.TASK_NOT_FOUND, exception.getErrorCode());
    assertTrue(exception.getMessage().contains(String.valueOf(taskId)));

    verify(taskRepository, times(1)).findById(taskId);
  }

  @Test
  void getTasksByMemberId() {
    // Given
    Member member = memberDataGenerator.createMember();
    List<Task> tasks = taskDataGenerator.createTasksWithMember(member);
    Long memberId = member.getId();

    // When
    when(memberRepository.existsById(memberId)).thenReturn(true);
    when(taskRepository.findByMemberId(memberId)).thenReturn(tasks);
    List<TaskDTO> result = taskService.getTasksByMemberId(memberId);

    // Then
    assertNotNull(result);
    for (TaskDTO taskDTO : result) {
      assertEquals(memberId, taskDTO.getMemberId());
    }

    verify(memberRepository, times(1)).existsById(memberId);
    verify(taskRepository, times(1)).findByMemberId(memberId);
  }

  @Test
  void getTasksByMemberId_NotFound() {
    // Given
    Long memberId = 1L;

    // When
    when(memberRepository.existsById(memberId)).thenReturn(false);

    // Then
    CustomException exception
        = assertThrows(CustomException.class, () -> taskService.getTasksByMemberId(memberId));
    assertEquals(ErrorCode.MEMBER_NOT_FOUND, exception.getErrorCode());
    assertTrue(exception.getMessage().contains(String.valueOf(memberId)));

    verify(memberRepository, times(1)).existsById(memberId);
    verify(taskRepository, never()).findByMemberId(anyLong());
  }


  @Test
  void getTasksByCategoryId() {
    // Given
    Category category = categoryDataGenerator.createCategory();
    List<Task> tasks = taskDataGenerator.createTasksWithCategory(category);
    Long categoryId = category.getId();

    // When
    when(categoryRepository.existsById(categoryId)).thenReturn(true);
    when(taskRepository.findByCategoryId(categoryId)).thenReturn(tasks);
    List<TaskDTO> result = taskService.getTasksByCategoryId(categoryId);

    // Then
    assertNotNull(result);
    for (TaskDTO taskDTO : result) {
      assertEquals(categoryId, taskDTO.getCategoryId());
    }

    verify(categoryRepository, times(1)).existsById(categoryId);
    verify(taskRepository, times(1)).findByCategoryId(categoryId);
  }

  @Test
  void getTasksByCategoryId_NotFound() {
    // Given
    Long categoryId = 1L;

    // When
    when(categoryRepository.existsById(categoryId)).thenReturn(false);

    // Then
    CustomException exception
        = assertThrows(CustomException.class, () -> taskService.getTasksByCategoryId(categoryId));
    assertEquals(ErrorCode.CATEGORY_NOT_FOUND, exception.getErrorCode());
    assertTrue(exception.getMessage().contains(String.valueOf(categoryId)));

    verify(categoryRepository, times(1)).existsById(categoryId);
    verify(taskRepository, never()).findByCategoryId(anyLong());
  }

}

