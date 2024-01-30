package com.hoadeol.busybuddy.mapper;

import com.hoadeol.busybuddy.dto.TaskDTO;
import com.hoadeol.busybuddy.model.Task;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  @Mapping(target = "memberId", source = "member.id")
  @Mapping(target = "categoryId", source = "category.id")
  TaskDTO toDTO(Task task);

  @Mapping(target = "member.id", source = "memberId")
  @Mapping(target = "category.id", source = "categoryId")
  Task toEntity(TaskDTO taskDTO);

  @IterableMapping(elementTargetType = TaskDTO.class)
  List<TaskDTO> toDTOList(List<Task> tasks);
}
