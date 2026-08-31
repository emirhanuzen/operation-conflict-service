package com.emirhanuzen.dto.vehicle;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateRequest {

    @NotBlank(message = "Plaka boş olamaz")
    private String plateNumber;

    @NotBlank(message = "Marka boş olamaz")
    private String brand;

    @NotBlank(message = "Model boş olamaz")
    private String model;
}