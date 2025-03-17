package com.task.processor.dto;

public class TaskStatistics {
  private long totalTasks;
  private long completedTasks;
  private long failedTasks;
  private long pendingTasks;
  private String successRate;

  public TaskStatistics(
      long totalTasks,
      long completedTasks,
      long failedTasks,
      long pendingTasks,
      String successRate) {
    this.totalTasks = totalTasks;
    this.completedTasks = completedTasks;
    this.failedTasks = failedTasks;
    this.pendingTasks = pendingTasks;
    this.successRate = successRate;
  }

  // Getters
  public long getTotalTasks() {
    return totalTasks;
  }

  public long getCompletedTasks() {
    return completedTasks;
  }

  public long getFailedTasks() {
    return failedTasks;
  }

  public long getPendingTasks() {
    return pendingTasks;
  }

  public String getSuccessRate() {
    return successRate;
  }
}
