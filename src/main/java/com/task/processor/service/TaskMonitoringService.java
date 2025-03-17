package com.task.processor.service;

import com.task.processor.dto.TaskStatistics;
import com.task.processor.model.TaskStatus;
import com.task.processor.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Math.round;

@Service
public class TaskMonitoringService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskMonitoringService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskStatistics getTaskStatistics() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus(TaskStatus.COMPLETED);
        long failedTasks = taskRepository.countByStatus(TaskStatus.FAILED);
        long pendingTasks = taskRepository.countByStatus(TaskStatus.PENDING);

        double successRate = (totalTasks > 0) ? (completedTasks * 100.0) / totalTasks : 0.0;

        return new TaskStatistics(totalTasks, completedTasks, failedTasks, pendingTasks, String.format("%.2f", successRate));
    }
}