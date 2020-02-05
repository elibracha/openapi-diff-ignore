package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.PathOperationSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathOperationValidator {

    private Map<String, Object> path;
    private ValidationResult result;

    public PathOperationValidator() {
        this.result = new ValidationResult();
    }

    public boolean validate() {
        List<String> supported = Arrays.stream(PathOperationSupport.values())
                .map(PathOperationSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<String, Object> entry : path.entrySet()) {
            if (!supported.contains(entry.getKey())) {
                result.setMessage(String.format("value \"%s\" not supported", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        return true;
    }

    public PathOperationValidator setPath(Map<String, Object> path) {
        this.path = path;
        return this;
    }

    public ValidationResult getResult() {
        return result;
    }
}
