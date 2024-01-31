package com.hoadeol.busybuddy.repository;

import com.hoadeol.busybuddy.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findByMemberId(Long memberId);

  List<Task> findByCategoryId(Long categoryId);
}
