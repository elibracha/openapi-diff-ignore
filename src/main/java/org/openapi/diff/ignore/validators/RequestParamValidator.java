package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.RequestParameterSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParamValidator {

    private Map<String, Object> params;
    private ValidationResult result;

    public boolean validate() {
        List<String> supported = Arrays.stream(RequestParameterSupport.values())
                .map(RequestParameterSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (supported.contains(entry.getKey())) {
                result.setMessage(String.format("value %s not supported", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        return true;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public ValidationResult getResult() {
        return result;
    }
}