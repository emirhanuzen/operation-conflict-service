package com.emirhanuzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emirhanuzen.entity.OperationTask;

public interface IOperationTaskRepository extends JpaRepository<OperationTask, Long> {
}