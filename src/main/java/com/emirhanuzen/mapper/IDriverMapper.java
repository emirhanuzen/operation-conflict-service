package com.emirhanuzen.mapper;

import org.mapstruct.Mapper;

import com.emirhanuzen.dto.driver.DriverCreateRequest;
import com.emirhanuzen.dto.driver.DriverResponse;
import com.emirhanuzen.dto.driver.DriverUpdateRequest;
import com.emirhanuzen.entity.Driver;

@Mapper(componentModel = "spring")
public interface IDriverMapper {

    Driver toEntity(DriverCreateRequest request);

    void updateEntityFromRequest(DriverUpdateRequest request, @org.mapstruct.MappingTarget Driver driver);

    DriverResponse toResponse(Driver driver);
}