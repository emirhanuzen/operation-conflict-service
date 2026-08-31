package com.emirhanuzen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emirhanuzen.dto.driver.DriverCreateRequest;
import com.emirhanuzen.dto.driver.DriverResponse;
import com.emirhanuzen.dto.driver.DriverUpdateRequest;
import com.emirhanuzen.entity.Driver;
import com.emirhanuzen.mapper.IDriverMapper;
import com.emirhanuzen.repository.IDriverRepository;
import com.emirhanuzen.service.IDriverService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DriverServiceImpl implements IDriverService {

    @Autowired
    private IDriverRepository driverRepository;

    @Autowired
    private IDriverMapper driverMapper;

    @Override
    public DriverResponse createDriver(DriverCreateRequest request) {
        Driver driver = driverMapper.toEntity(request);
        Driver saved = driverRepository.save(driver);
        return driverMapper.toResponse(saved);
    }

    @Override
    public DriverResponse updateDriver(Long id, DriverUpdateRequest request) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sürücü bulunamadı: " + id));

        driverMapper.updateEntityFromRequest(request, driver);
        Driver updated = driverRepository.save(driver);
        return driverMapper.toResponse(updated);
    }

    @Override
    public DriverResponse getDriverById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sürücü bulunamadı: " + id));
        return driverMapper.toResponse(driver);
    }

    @Override
    public List<DriverResponse> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(driverMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new EntityNotFoundException("Sürücü bulunamadı: " + id);
        }
        driverRepository.deleteById(id);
    }
}