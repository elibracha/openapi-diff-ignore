package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.OperationSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationValidator {

    private Map<String, Object> operations;
    private ValidationResult result;

    public OperationValidator() {
        this.result = new ValidationResult();
    }

    public boolean validate() {
        List<String> supported = Arrays.stream(OperationSupport.values())
                .map(OperationSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<String, Object> entry : operations.entrySet()) {
            if (!supported.contains(entry.getKey())) {
                result.setMessage(String.format("the method \"%s\" not a valid http method", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        return true;
    }

    public OperationValidator setOperations(Map<String, Object> operations) {
        this.operations = operations;
        return this;
    }

    public ValidationResult getResult() {
        return result;
    }
}
