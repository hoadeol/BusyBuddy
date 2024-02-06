package com.hoadeol.busybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hoadeol.busybuddy.constants.ErrorCode;
import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.exception.CustomException;
import com.hoadeol.busybuddy.mapper.TaskMapper;
import com.hoadeol.busybuddy.model.Category;
import com.hoadeol.busybuddy.model.Member;
import com.hoadeol.busybuddy.model.Priority;
import com.hoadeol.busybuddy.model.Task;
import com.hoadeol.busybuddy.repository.CategoryRepository;
import com.hoadeol.busybuddy.repository.MemberRepository;
import com.hoadeol.busybuddy.repository.TaskRepository;
import com.hoadeol.busybuddy.util.dto.TaskDTODummyDataGenerator;
import com.hoadeol.busybuddy.util.entity.CategoryDummyDataGenerator;
import com.hoadeol.busybuddy.util.entity.MemberDummyDataGenerator;
import com.hoadeol.busybuddy.util.entity.TaskDummyDataGenerator;
import java.time.LocalDate;
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
  private final TaskDTODummyDataGenerator taskDTODataGenerator = new TaskDTODummyDataGenerator();

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

  @Test
  void saveTask() {
    // Given
    TaskDTO taskDTO = taskDTODataGenerator.createSavedTaskDTO();
    Task expectedTask = TaskMapper.INSTANCE.toEntity(taskDTO);

    // When
    when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);
    TaskDTO result = taskService.saveTask(taskDTO);

    //then
    thenSavedTask(taskDTO, result);
    assertEquals(taskDTO.getStartDate(), result.getStartDate());
  }

  @Test
  void saveTask_Today() throws Exception {
    // Given
    TaskDTO taskDTO = taskDTODataGenerator.createSavedTaskDTO();
    Task expectedTask = getExpectedTodayTask(taskDTO);

    // When
    when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);
    TaskDTO result = taskService.saveTask(taskDTO, true);

    // Then
    thenSavedTask(taskDTO, result);
    assertEquals(LocalDate.now(), result.getStartDate());
  }

  private void thenSavedTask(TaskDTO taskDTO, TaskDTO result) {
    // Then
    assertNotNull(result);
    verify(taskRepository, times(1)).save(any(Task.class));

    // same values
    assertEquals(taskDTO.getMemberId(), result.getMemberId());
    assertEquals(taskDTO.getCategoryId(), result.getCategoryId());
    assertEquals(taskDTO.getTitle(), result.getTitle());
    assertEquals(taskDTO.getContent(), result.getContent());
    assertEquals(taskDTO.getDueDate(), result.getDueDate());

    // Handle priority
    String expectedPriority =
        taskDTO.getPriority() == null ? String.valueOf(Priority.NORMAL) : taskDTO.getPriority();
    assertEquals(expectedPriority, result.getPriority());

    // Handle isCompleted and completeDate
    assertEquals(taskDTO.getIsCompleted(), result.getIsCompleted());
    assertEquals(Optional.ofNullable(taskDTO.getIsCompleted()).orElse(false) ?
        taskDTO.getCompleteDate() : null, result.getCompleteDate());
  }

  private Task getExpectedTodayTask(TaskDTO taskDTO) throws Exception {
    TaskDTO cloneDTO = taskDTO.clone();
    cloneDTO.setStartDate(LocalDate.now());
    return TaskMapper.INSTANCE.toEntity(cloneDTO);
  }

}

