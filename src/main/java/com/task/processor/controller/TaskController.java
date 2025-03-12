package com.task.processor.controller;

import com.task.processor.model.Task;
import com.task.processor.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<Task> submitTask(@RequestParam String name, @RequestParam String payload) {
    return ResponseEntity.ok(taskService.submitTask(name, payload));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTask(@PathVariable UUID id) {
    Optional<Task> task = taskService.getTaskById(id);
    return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks());
  }
}
