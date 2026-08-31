package com.emirhanuzen.service;

import java.util.List;

import com.emirhanuzen.dto.vehicle.VehicleCreateRequest;
import com.emirhanuzen.dto.vehicle.VehicleResponse;
import com.emirhanuzen.dto.vehicle.VehicleUpdateRequest;

public interface IVehicleService {

    VehicleResponse createVehicle(VehicleCreateRequest request);

    VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request);

    VehicleResponse getVehicleById(Long id);

    List<VehicleResponse> getAllVehicles();

    void deleteVehicle(Long id);
}