package com.hoadeol.busybuddy.repository;

import com.hoadeol.busybuddy.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
