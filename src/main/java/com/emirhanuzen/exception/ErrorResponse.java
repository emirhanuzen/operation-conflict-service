package com.emirhanuzen.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private Long conflictingAssignmentId;

    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }
}