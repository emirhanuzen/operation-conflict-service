package com.emirhanuzen.exception;

public class AssignmentConflictException extends RuntimeException {

    private final Long conflictingAssignmentId;

    public AssignmentConflictException(String message, Long conflictingAssignmentId) {
        super(message);
        this.conflictingAssignmentId = conflictingAssignmentId;
    }

    public Long getConflictingAssignmentId() {
        return conflictingAssignmentId;
    }
}