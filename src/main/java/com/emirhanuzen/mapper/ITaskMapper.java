package com.emirhanuzen.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;

import com.emirhanuzen.dto.task.TaskCreateRequest;
import com.emirhanuzen.dto.task.TaskResponse;
import com.emirhanuzen.dto.task.TaskUpdateRequest;
import com.emirhanuzen.entity.OperationTask;

@Mapper(componentModel = "spring")
public interface ITaskMapper {

    OperationTask toEntity(TaskCreateRequest request);

    void updateEntityFromRequest(TaskUpdateRequest request, @MappingTarget OperationTask task);

    TaskResponse toResponse(OperationTask task);
}