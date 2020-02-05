package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.RequestValidationResult;
import org.openapi.diff.ignore.models.validations.enums.RequestSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParamValidator {

    private Map<String, Object> request;
    private RequestValidationResult result;

    public boolean validate() {
        List<String> supported = Arrays.stream(RequestSupport.values())
                .map(RequestSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<String, Object> entry : request.entrySet()) {
            if (supported.contains(entry.getKey())) {
                result.setMessage(String.format("value : %s not supported", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        return true;
    }

    public void setRequst(Map<String, Object> request) {
        this.request = request;
    }

    public RequestValidationResult getResult() {
        return result;
    }
}