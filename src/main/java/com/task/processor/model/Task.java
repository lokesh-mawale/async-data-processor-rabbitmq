package com.task.processor.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private String payload;

  @Enumerated(EnumType.STRING)
  private TaskStatus status;
}
