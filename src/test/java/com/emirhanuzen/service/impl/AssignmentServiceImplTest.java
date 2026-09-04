package com.emirhanuzen.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emirhanuzen.dto.assignment.AssignmentCreateRequest;
import com.emirhanuzen.dto.assignment.AssignmentResponse;
import com.emirhanuzen.entity.Assignment;
import com.emirhanuzen.entity.Driver;
import com.emirhanuzen.entity.OperationTask;
import com.emirhanuzen.entity.Vehicle;
import com.emirhanuzen.exception.AssignmentConflictException;
import com.emirhanuzen.mapper.IAssignmentMapper;
import com.emirhanuzen.repository.IAssignmentRepository;
import com.emirhanuzen.repository.IDriverRepository;
import com.emirhanuzen.repository.ITaskRepository;
import com.emirhanuzen.repository.IVehicleRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

    @Mock
    private IAssignmentRepository assignmentRepository;

    @Mock
    private IDriverRepository driverRepository;

    @Mock
    private IVehicleRepository vehicleRepository;

    @Mock
    private ITaskRepository taskRepository;

    @Mock
    private IAssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    private AssignmentCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new AssignmentCreateRequest();
        request.setServiceDate(LocalDate.of(2026, 9, 5));
        request.setDriverId(1L);
        request.setVehicleId(1L);
        request.setTaskId(1L);
        request.setStartTime(LocalDateTime.of(2026, 9, 5, 9, 0));
        request.setEndTime(LocalDateTime.of(2026, 9, 5, 12, 0));
    }

    @Test
    void createAssignment_shouldThrowConflictException_whenDriverHasOverlappingAssignment() {
        Assignment existingAssignment = new Assignment();
        existingAssignment.setId(99L);
        existingAssignment.setStartTime(LocalDateTime.of(2026, 9, 5, 8, 0));
        existingAssignment.setEndTime(LocalDateTime.of(2026, 9, 5, 10, 0));

        List<Assignment> conflicts = Collections.singletonList(existingAssignment);

        when(assignmentRepository.findConflictingByDriver(
                request.getDriverId(), request.getStartTime(), request.getEndTime()))
                .thenReturn(conflicts);

        assertThrows(AssignmentConflictException.class, () -> {
            assignmentService.createAssignment(request);
        });
    }

    @Test
    void createAssignment_shouldSucceed_whenNoConflictExists() {
        when(assignmentRepository.findConflictingByDriver(
                request.getDriverId(), request.getStartTime(), request.getEndTime()))
                .thenReturn(Collections.emptyList());

        when(assignmentRepository.findConflictingByVehicle(
                request.getVehicleId(), request.getStartTime(), request.getEndTime()))
                .thenReturn(Collections.emptyList());

        Driver driver = new Driver();
        driver.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        OperationTask task = new OperationTask();
        task.setId(1L);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Assignment mappedEntity = new Assignment();
        when(assignmentMapper.toEntity(request)).thenReturn(mappedEntity);

        Assignment savedEntity = new Assignment();
        savedEntity.setId(10L);
        when(assignmentRepository.save(mappedEntity)).thenReturn(savedEntity);

        AssignmentResponse expectedResponse = new AssignmentResponse();
        expectedResponse.setId(10L);
        when(assignmentMapper.toResponse(savedEntity)).thenReturn(expectedResponse);

        AssignmentResponse result = assignmentService.createAssignment(request);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void createAssignment_shouldThrowIllegalArgumentException_whenStartTimeIsAfterEndTime() {
        request.setStartTime(LocalDateTime.of(2026, 9, 5, 15, 0));
        request.setEndTime(LocalDateTime.of(2026, 9, 5, 10, 0));

        assertThrows(IllegalArgumentException.class, () -> {
            assignmentService.createAssignment(request);
        });
    }

    @Test
    void createAssignment_shouldThrowEntityNotFoundException_whenDriverDoesNotExist() {
        when(assignmentRepository.findConflictingByDriver(
                request.getDriverId(), request.getStartTime(), request.getEndTime()))
                .thenReturn(Collections.emptyList());

        when(assignmentRepository.findConflictingByVehicle(
                request.getVehicleId(), request.getStartTime(), request.getEndTime()))
                .thenReturn(Collections.emptyList());

        when(driverRepository.findById(request.getDriverId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            assignmentService.createAssignment(request);
        });
    }
}