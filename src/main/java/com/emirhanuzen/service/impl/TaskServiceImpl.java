package com.emirhanuzen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emirhanuzen.dto.task.TaskCreateRequest;
import com.emirhanuzen.dto.task.TaskResponse;
import com.emirhanuzen.dto.task.TaskUpdateRequest;
import com.emirhanuzen.entity.OperationTask;
import com.emirhanuzen.mapper.ITaskMapper;
import com.emirhanuzen.repository.ITaskRepository;
import com.emirhanuzen.service.ITaskService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private ITaskMapper taskMapper;

    @Override
    public TaskResponse createTask(TaskCreateRequest request) {
        OperationTask task = taskMapper.toEntity(request);
        OperationTask saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        OperationTask task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Görev bulunamadı: " + id));

        taskMapper.updateEntityFromRequest(request, task);
        OperationTask updated = taskRepository.save(task);
        return taskMapper.toResponse(updated);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        OperationTask task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Görev bulunamadı: " + id));
        return taskMapper.toResponse(task);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Görev bulunamadı: " + id);
        }
        taskRepository.deleteById(id);
    }
}