package com.emirhanuzen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.emirhanuzen.dto.vehicle.VehicleCreateRequest;
import com.emirhanuzen.dto.vehicle.VehicleResponse;
import com.emirhanuzen.dto.vehicle.VehicleUpdateRequest;
import com.emirhanuzen.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface IVehicleMapper {

    Vehicle toEntity(VehicleCreateRequest request);

    void updateEntityFromRequest(VehicleUpdateRequest request, @MappingTarget Vehicle vehicle);

    VehicleResponse toResponse(Vehicle vehicle);
}