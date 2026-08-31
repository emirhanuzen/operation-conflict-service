package com.emirhanuzen.dto.assignment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.emirhanuzen.entity.AssignmentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {

    private Long id;
    private LocalDate serviceDate;
    private Long taskId;
    private Long driverId;
    private Long vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AssignmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}