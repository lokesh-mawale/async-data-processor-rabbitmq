package com.task.processor.service;

import com.task.processor.config.RabbitMQProperties;
import com.task.processor.model.Task;
import com.task.processor.model.TaskStatus;
import com.task.processor.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class TaskServiceImpl implements TaskService {

  @Autowired private RabbitTemplate rabbitTemplate;

  @Autowired private TaskRepository taskRepository;

  @Autowired private RabbitMQProperties rabbitMQProperties;

  public Task submitTask(String name, String payload) {
    Task task = new Task();
    task.setName(name);
    task.setPayload(payload);
    task.setStatus(TaskStatus.PENDING);
    try {
      task = taskRepository.save(task);

      rabbitTemplate.convertAndSend(
          rabbitMQProperties.getExchange(),
          rabbitMQProperties.getRoutingKey(),
          task.getId().toString());
    } catch (AmqpException e) {
      throw new RuntimeException(e);
    }
    return task;
  }

  public Optional<Task> getTaskById(UUID taskId) {
    return taskRepository.findById(taskId);
  }

  @Async
  @RabbitListener(queues = "#{rabbitMQProperties.queue}")
  public void processTask(UUID taskId) {
    taskRepository
        .findById(taskId)
        .ifPresent(
            task -> {
              try {
                if (Math.random() < 0.2) { // Simulate a 20% failure rate
                  throw new RuntimeException("Task processing failed due to an error.");
                }
                task.setStatus(TaskStatus.PROCESSING);
                taskRepository.save(task);
                Thread.sleep(5000);
                task.setStatus(TaskStatus.COMPLETED);
              } catch (Exception e) {
                task.setStatus(TaskStatus.FAILED);
              } finally {
                taskRepository.save(task);
              }
            });
  }

  @Override
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }
}
