package com.emirhanuzen.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.emirhanuzen.dto.driver.DriverCreateRequest;
import com.emirhanuzen.dto.driver.DriverResponse;
import com.emirhanuzen.service.IDriverService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AssignmentIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private IDriverService driverService;

    @Test
    void createDriver_shouldPersistToRealDatabase() {
        DriverCreateRequest request = new DriverCreateRequest();
        request.setFullName("Test Sürücü");
        request.setLicenseNumber("06TEST999");
        request.setLicenseClass("B");
        request.setPhoneNumber("+905551112233");

        DriverResponse response = driverService.createDriver(request);

        assertEquals("Test Sürücü", response.getFullName());
        assertEquals("06TEST999", response.getLicenseNumber());
    }
}