package com.emirhanuzen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emirhanuzen.dto.vehicle.VehicleCreateRequest;
import com.emirhanuzen.dto.vehicle.VehicleResponse;
import com.emirhanuzen.dto.vehicle.VehicleUpdateRequest;
import com.emirhanuzen.entity.Vehicle;
import com.emirhanuzen.mapper.IVehicleMapper;
import com.emirhanuzen.repository.IVehicleRepository;
import com.emirhanuzen.service.IVehicleService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Autowired
    private IVehicleMapper vehicleMapper;

    @Override
    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        Vehicle vehicle = vehicleMapper.toEntity(request);
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(saved);
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + id));

        vehicleMapper.updateEntityFromRequest(request, vehicle);
        Vehicle updated = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(updated);
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + id));
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new EntityNotFoundException("Araç bulunamadı: " + id);
        }
        vehicleRepository.deleteById(id);
    }
}