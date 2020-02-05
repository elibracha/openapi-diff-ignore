package org.openapi.diff.ignore.models.validations;

import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Objects;

public class RequestValidationResult {

    private String message;
    private ValidationStatus validationStatus;

    public RequestValidationResult() {
    }

    public RequestValidationResult(String message, ValidationStatus validationStatus) {
        this.message = message;
        this.validationStatus = validationStatus;
    }

    public String getMessage() {
        return message;
    }

    public RequestValidationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public RequestValidationResult setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestValidationResult that = (RequestValidationResult) o;
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
