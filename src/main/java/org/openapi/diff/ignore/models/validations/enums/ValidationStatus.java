package org.openapi.diff.ignore.models.validations.enums;

public enum ValidationStatus {
    BAD_IGNORE_FILE(400, "BAD IGNORE FILE");

    private int status;
    private String message;

    ValidationStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
