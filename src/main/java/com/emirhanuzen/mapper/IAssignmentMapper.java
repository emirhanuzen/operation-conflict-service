package com.emirhanuzen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.emirhanuzen.dto.assignment.AssignmentCreateRequest;
import com.emirhanuzen.dto.assignment.AssignmentResponse;
import com.emirhanuzen.dto.assignment.AssignmentUpdateRequest;
import com.emirhanuzen.entity.Assignment;

@Mapper(componentModel = "spring")
public interface IAssignmentMapper {

    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Assignment toEntity(AssignmentCreateRequest request);

    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(AssignmentUpdateRequest request, @MappingTarget Assignment assignment);

    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "taskId", source =   "task.id")
    AssignmentResponse toResponse(Assignment assignment);
}