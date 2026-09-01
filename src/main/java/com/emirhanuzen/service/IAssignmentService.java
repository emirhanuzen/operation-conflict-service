package com.emirhanuzen.service;

import java.util.List;

import com.emirhanuzen.dto.assignment.AssignmentCreateRequest;
import com.emirhanuzen.dto.assignment.AssignmentResponse;
import com.emirhanuzen.dto.assignment.AssignmentUpdateRequest;

public interface IAssignmentService {

    AssignmentResponse createAssignment(AssignmentCreateRequest request);

    AssignmentResponse updateAssignment(Long id, AssignmentUpdateRequest request);

    AssignmentResponse getAssignmentById(Long id);

    List<AssignmentResponse> getAllAssignments();

    void deleteAssignment(Long id);
}