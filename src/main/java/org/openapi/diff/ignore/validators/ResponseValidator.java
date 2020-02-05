package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.ResponseSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseValidator {

    private Map<String, Object> response;
    private ValidationResult result;

    public ResponseValidator() {
        this.result = new ValidationResult();
    }

    public boolean validate() {
        List<String> supported = Arrays.stream(ResponseSupport.values())
                .map(ResponseSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<String, Object> entry : response.entrySet()) {
            if (!supported.contains(entry.getKey())) {
                result.setMessage(String.format("value \"%s\" not supported int response", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }
        return true;
    }

    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }

    public ValidationResult getResult() {
        return result;
    }
}
