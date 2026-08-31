package com.emirhanuzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emirhanuzen.entity.Vehicle;

public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {
}