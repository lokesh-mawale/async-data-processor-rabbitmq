package com.task.processor.service;

import com.task.processor.model.Task;
import com.task.processor.model.TaskStatus;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

  public Task submitTask(String name, String payload);

  public Optional<Task> getTaskById(UUID taskId);

  public void processTask(UUID taskId);

  List<Task> getAllTasks();
}
