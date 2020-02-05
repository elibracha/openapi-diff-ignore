package org.openapi.diff.ignore.models.validations;

import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Objects;

public class ValidationResult {

    private String message;
    private ValidationStatus validationStatus;

    public String getMessage() {
        return message;
    }

    public ValidationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public ValidationResult setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResult that = (ValidationResult) o;
        return Objects.equals(message, that.message) &&
                validationStatus == that.validationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, validationStatus);
    }

    @Override
    public String toString() {
        return "RequestValidationResult{" +
                ", message='" + message + '\'' +
                ", validationStatus=" + validationStatus +
                '}';
    }
}
