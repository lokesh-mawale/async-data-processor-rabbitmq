package com.task.processor.repository;

import com.task.processor.model.Task;
import com.task.processor.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    long countByStatus(TaskStatus status);
}
