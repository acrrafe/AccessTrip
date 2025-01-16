package com.example.acccesstrip.exception;

import java.util.Map;

public class BadRequestException extends RuntimeException {

    private final int errorId;
    private final String errorMessage;
    private final Map<String, Object> errorDetails;

    public BadRequestException(String message, int errorId, String errorMessage, Map<String, Object> errorDetails) {
        super(message);
        this.errorId = errorId;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public int getErrorId() {
        return errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Object> getErrorDetails() {
        return errorDetails;
    }
}
