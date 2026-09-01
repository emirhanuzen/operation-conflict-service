package com.emirhanuzen.service;

import java.util.List;

import com.emirhanuzen.dto.task.TaskCreateRequest;
import com.emirhanuzen.dto.task.TaskResponse;
import com.emirhanuzen.dto.task.TaskUpdateRequest;

public interface ITaskService {

    TaskResponse createTask(TaskCreateRequest request);

    TaskResponse updateTask(Long id, TaskUpdateRequest request);

    TaskResponse getTaskById(Long id);

    List<TaskResponse> getAllTasks();

    void deleteTask(Long id);
}	