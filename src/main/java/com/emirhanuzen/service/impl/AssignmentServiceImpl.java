package com.emirhanuzen.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.emirhanuzen.dto.assignment.AssignmentCreateRequest;
import com.emirhanuzen.dto.assignment.AssignmentResponse;
import com.emirhanuzen.dto.assignment.AssignmentUpdateRequest;
import com.emirhanuzen.entity.Assignment;
import com.emirhanuzen.entity.AssignmentStatus;
import com.emirhanuzen.entity.Driver;
import com.emirhanuzen.entity.OperationTask;
import com.emirhanuzen.entity.Vehicle;
import com.emirhanuzen.exception.AssignmentConflictException;
import com.emirhanuzen.mapper.IAssignmentMapper;
import com.emirhanuzen.repository.IAssignmentRepository;
import com.emirhanuzen.repository.IDriverRepository;
import com.emirhanuzen.repository.ITaskRepository;
import com.emirhanuzen.repository.IVehicleRepository;
import com.emirhanuzen.service.IAssignmentService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AssignmentServiceImpl implements IAssignmentService {

    @Autowired
    private IAssignmentRepository assignmentRepository;

    @Autowired
    private IDriverRepository driverRepository;

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private IAssignmentMapper assignmentMapper;

    @Override
    public AssignmentResponse createAssignment(AssignmentCreateRequest request) {

        // 1. Zaman aralığı geçerli mi kontrol et
        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new IllegalArgumentException("Başlangıç zamanı, bitiş zamanından önce olmalıdır");
        }

        // 2. Sürücü çakışma kontrolü
        checkDriverConflict(request.getDriverId(), request.getStartTime(), request.getEndTime());

        // 3. Araç çakışma kontrolü
        checkVehicleConflict(request.getVehicleId(), request.getStartTime(), request.getEndTime());

        // 4. İlişkili nesneleri bul
        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Sürücü bulunamadı: " + request.getDriverId()));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + request.getVehicleId()));

        OperationTask task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Görev bulunamadı: " + request.getTaskId()));

        // 5. Entity'yi oluştur
        Assignment assignment = assignmentMapper.toEntity(request);
        assignment.setDriver(driver);
        assignment.setVehicle(vehicle);
        assignment.setTask(task);
        assignment.setStatus(AssignmentStatus.PLANNED);
        assignment.setCreatedAt(LocalDateTime.now());

        Assignment saved = assignmentRepository.save(assignment);
        return assignmentMapper.toResponse(saved);
    }

    @Override
    public AssignmentResponse updateAssignment(Long id, AssignmentUpdateRequest request) {

        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atama bulunamadı: " + id));

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new IllegalArgumentException("Başlangıç zamanı, bitiş zamanından önce olmalıdır");
        }

        // Çakışma kontrolünde kendi kaydını hariç tutmamız lazım (aşağıda düzelteceğiz)
        checkDriverConflictExcludingSelf(request.getDriverId(), request.getStartTime(), request.getEndTime(), id);
        checkVehicleConflictExcludingSelf(request.getVehicleId(), request.getStartTime(), request.getEndTime(), id);

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Sürücü bulunamadı: " + request.getDriverId()));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + request.getVehicleId()));

        OperationTask task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Görev bulunamadı: " + request.getTaskId()));

        assignmentMapper.updateEntityFromRequest(request, assignment);
        assignment.setDriver(driver);
        assignment.setVehicle(vehicle);
        assignment.setTask(task);
        assignment.setUpdatedAt(LocalDateTime.now());

        Assignment updated = assignmentRepository.save(assignment);
        return assignmentMapper.toResponse(updated);
    }

    @Override
    public AssignmentResponse getAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atama bulunamadı: " + id));
        return assignmentMapper.toResponse(assignment);
    }

    @Override
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepository.findAll()
                .stream()
                .map(assignmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AssignmentResponse> getFilteredAssignments(LocalDate serviceDate, Long driverId, Long vehicleId, Pageable pageable) {
        Page<Assignment> assignments = assignmentRepository.findWithFilters(serviceDate, driverId, vehicleId, pageable);
        return assignments.map(assignmentMapper::toResponse);
    }
    
    @Override
    public void deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Atama bulunamadı: " + id);
        }
        assignmentRepository.deleteById(id);
    }

    // ---- Çakışma Kontrol Yardımcı Methodları ----

    private void checkDriverConflict(Long driverId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Assignment> conflicts = assignmentRepository.findConflictingByDriver(driverId, startTime, endTime);
        if (!conflicts.isEmpty()) {
            Assignment conflict = conflicts.get(0);
            throw new AssignmentConflictException(
                    "Sürücü zaten " + conflict.getStartTime() + " - " + conflict.getEndTime() + " arasında atanmış.",
                    conflict.getId());
        }
    }

    private void checkVehicleConflict(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Assignment> conflicts = assignmentRepository.findConflictingByVehicle(vehicleId, startTime, endTime);
        if (!conflicts.isEmpty()) {
            Assignment conflict = conflicts.get(0);
            throw new AssignmentConflictException(
                    "Araç zaten " + conflict.getStartTime() + " - " + conflict.getEndTime() + " arasında atanmış.",
                    conflict.getId());
        }
    }

    private void checkDriverConflictExcludingSelf(Long driverId, LocalDateTime startTime, LocalDateTime endTime, Long selfId) {
        List<Assignment> conflicts = assignmentRepository.findConflictingByDriver(driverId, startTime, endTime)
                .stream()
                .filter(a -> !a.getId().equals(selfId))
                .collect(Collectors.toList());
        if (!conflicts.isEmpty()) {
            Assignment conflict = conflicts.get(0);
            throw new AssignmentConflictException(
                    "Sürücü zaten " + conflict.getStartTime() + " - " + conflict.getEndTime() + " arasında atanmış.",
                    conflict.getId());
        }
    }

    private void checkVehicleConflictExcludingSelf(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime, Long selfId) {
        List<Assignment> conflicts = assignmentRepository.findConflictingByVehicle(vehicleId, startTime, endTime)
                .stream()
                .filter(a -> !a.getId().equals(selfId))
                .collect(Collectors.toList());
        if (!conflicts.isEmpty()) {
            Assignment conflict = conflicts.get(0);
            throw new AssignmentConflictException(
                    "Araç zaten " + conflict.getStartTime() + " - " + conflict.getEndTime() + " arasında atanmış.",
                    conflict.getId());
        }
    }
}