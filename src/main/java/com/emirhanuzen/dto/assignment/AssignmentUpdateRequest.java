package com.emirhanuzen.dto.assignment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.emirhanuzen.entity.AssignmentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentUpdateRequest {

    @NotNull(message = "Servis tarihi boş olamaz")
    private LocalDate serviceDate;

    @NotNull(message = "Görev ID boş olamaz")
    private Long taskId;

    @NotNull(message = "Sürücü ID boş olamaz")
    private Long driverId;

    @NotNull(message = "Araç ID boş olamaz")
    private Long vehicleId;

    @NotNull(message = "Başlangıç zamanı boş olamaz")
    private LocalDateTime startTime;

    @NotNull(message = "Bitiş zamanı boş olamaz")
    private LocalDateTime endTime;

    @NotNull(message = "Durum boş olamaz")
    private AssignmentStatus status;
}