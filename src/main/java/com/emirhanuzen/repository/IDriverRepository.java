package com.emirhanuzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emirhanuzen.entity.Driver;

public interface IDriverRepository extends JpaRepository<Driver, Long> {
}