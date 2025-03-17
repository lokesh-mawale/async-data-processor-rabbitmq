package com.task.processor.controller;

import com.task.processor.dto.TaskStatistics;
import com.task.processor.service.TaskMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskMonitoringController {

    private final TaskMonitoringService taskMonitoringService;

    @Autowired
    public TaskMonitoringController(TaskMonitoringService taskMonitoringService) {
        this.taskMonitoringService = taskMonitoringService;
    }

    @GetMapping("/stats")
    public ResponseEntity<TaskStatistics> getTaskStatistics() {
        return ResponseEntity.ok(taskMonitoringService.getTaskStatistics());
    }
}
