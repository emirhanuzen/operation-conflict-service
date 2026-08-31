package com.emirhanuzen.service;

import java.util.List;

import com.emirhanuzen.dto.driver.DriverCreateRequest;
import com.emirhanuzen.dto.driver.DriverResponse;
import com.emirhanuzen.dto.driver.DriverUpdateRequest;

public interface IDriverService {

    DriverResponse createDriver(DriverCreateRequest request);

    DriverResponse updateDriver(Long id, DriverUpdateRequest request);

    DriverResponse getDriverById(Long id);

    List<DriverResponse> getAllDrivers();

    void deleteDriver(Long id);
}