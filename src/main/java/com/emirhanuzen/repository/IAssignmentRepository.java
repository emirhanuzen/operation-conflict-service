package com.emirhanuzen.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emirhanuzen.entity.Assignment;

public interface IAssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT a FROM Assignment a WHERE a.driver.id = :driverId " +
           "AND a.startTime < :endTime AND a.endTime > :startTime")
    List<Assignment> findConflictingByDriver(
            @Param("driverId") Long driverId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM Assignment a WHERE a.vehicle.id = :vehicleId " +
           "AND a.startTime < :endTime AND a.endTime > :startTime")
    List<Assignment> findConflictingByVehicle(
            @Param("vehicleId") Long vehicleId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM Assignment a WHERE " +
            "(:serviceDate IS NULL OR a.serviceDate = :serviceDate) AND " +
            "(:driverId IS NULL OR a.driver.id = :driverId) AND " +
            "(:vehicleId IS NULL OR a.vehicle.id = :vehicleId)")
    	Page<Assignment> findWithFilters(
             @Param("serviceDate") LocalDate serviceDate,
             @Param("driverId") Long driverId,
             @Param("vehicleId") Long vehicleId,
             Pageable pageable);
    
}