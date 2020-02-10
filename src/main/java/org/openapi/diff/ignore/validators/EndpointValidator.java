package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Map;

public class EndpointValidator {

    private static final String ENDPOINT_PATTERN = "(\\/[a-zA-Z{}1-9$*\\/]+)";

    private OperationValidator pathOperationValidator;
    private Map<String, Object> endpoints;
    private ValidationResult result;

    public EndpointValidator() {
        this.result = new ValidationResult();
        this.pathOperationValidator = new OperationValidator();
    }

    public boolean validate() {

        for (Map.Entry<String, Object> entry : endpoints.entrySet()) {
            if (!entry.getKey().matches(ENDPOINT_PATTERN)) {
                result.setMessage(String.format("value %s not a valid endpoint", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            pathOperationValidator.setOperations((Map<String, Object>) entry.getValue());

            if (!pathOperationValidator.validate()) {
                this.result = pathOperationValidator.getResult();
                return false;
            }
        }

        return true;
    }

    public void setEndpoints(Map<String, Object> endpoints) {
        this.endpoints = endpoints;
    }

    public ValidationResult getResult() {
        return result;
    }
}
