package com.task.processor.service;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import com.task.processor.config.RabbitMQProperties;
import com.task.processor.model.Task;
import com.task.processor.model.TaskStatus;
import com.task.processor.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @Mock private RabbitTemplate rabbitTemplate;
  @Mock private TaskRepository taskRepository;
  @Mock private RabbitMQProperties rabbitMQProperties;

  @InjectMocks private TaskServiceImpl taskService;

  private UUID taskId;
  private Task mockTask;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    taskId = UUID.randomUUID();
    mockTask = new Task(taskId, "Test Task", "Payload Data", TaskStatus.PENDING);
  }

  @Test
  void testSubmitTask() {
    // Arrange
    stubForRepositoryAndProperties();

    // Act
    Task result = taskService.submitTask("Test Task", "Payload Data");

    // Assert
    verify(taskRepository, times(1)).save(any(Task.class));
    verify(rabbitTemplate, times(1))
            .convertAndSend(eq("exchange"), eq("routingKey"), eq(taskId.toString()));

    assert result.getName().equals("Test Task");
    assert result.getStatus() == TaskStatus.PENDING;
  }

  @Test
  void testSubmitTask_WhenRabbitMQFails() {
    // Arrange
    stubForRepositoryAndProperties();

    doThrow(new AmqpException("RabbitMQ Error"))
            .when(rabbitTemplate)
            .convertAndSend(anyString(), anyString(), anyString());

    // Act & Assert
    assertThrows(RuntimeException.class, () -> taskService.submitTask("Test Task", "Payload Data"));
  }


  @Test
  void testGetTaskById() {
    // Arrange
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

    // Act
    Optional<Task> result = taskService.getTaskById(taskId);

    // Assert
    assert result.isPresent();
    assert result.get().getId().equals(taskId);
  }

  @Test
  void testProcessTask_Success() throws Exception {
    // Arrange
    mockTask.setStatus(TaskStatus.PENDING);
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

    // Act
    taskService.processTask(taskId);

    // Assert
    verify(taskRepository, times(2)).save(any(Task.class)); // Processing + Completed
    assert mockTask.getStatus() == TaskStatus.COMPLETED;
  }

  @Test
  void testProcessTask_Failure() {
    // Arrange
    mockTask.setStatus(TaskStatus.PENDING);
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

    doThrow(new RuntimeException("Processing error"))
            .doReturn(mockTask) // Return a valid Task on the second invocation
            .when(taskRepository)
            .save(any(Task.class));


    // Act
    taskService.processTask(taskId);

    // Assert
    assert mockTask.getStatus() == TaskStatus.FAILED;
  }

  private void stubForRepositoryAndProperties() {
    when(taskRepository.save(any(Task.class))).thenReturn(mockTask);
    when(rabbitMQProperties.getExchange()).thenReturn("exchange");
    when(rabbitMQProperties.getRoutingKey()).thenReturn("routingKey");
  }
}
