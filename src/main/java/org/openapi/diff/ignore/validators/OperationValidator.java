package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.HttpMethodSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationValidator {

    private Map<String, Object> operations;
    private ValidationResult result;

    private ResponseValidator responseValidator;
    private RequestValidator requestValidator;

    public OperationValidator() {
        this.result = new ValidationResult();
        this.requestValidator = new RequestValidator();
        this.responseValidator = new ResponseValidator();
    }

    public boolean validate() {
        List<String> supported = Arrays.stream(HttpMethodSupport.values())
                .map(HttpMethodSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<String, Object> entry : operations.entrySet()) {
            if (!supported.contains(entry.getKey())) {
                result.setMessage(String.format("the method \"%s\" not a valid entry in operation method", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            if (entry.getValue() instanceof Map) {
                if (((Map<String, Object>) entry.getValue()).containsKey("security")) {
                    if (!(((Map<String, Object>) entry.getValue()).get("security") instanceof ArrayList)) {
                        result.setMessage("the security field most be a list").setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                        return false;
                    }
                }

                if (((Map<String, Object>) entry.getValue()).containsKey("parameters")) {
                    if (!(((Map<String, Object>) entry.getValue()).get("parameters") instanceof ArrayList)) {
                        result.setMessage("the parameters field most be a list").setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                        return false;
                    }
                }

                if (((Map<String, Object>) entry.getValue()).containsKey("request")) {
                    requestValidator.setRequest((Map<String, Object>) ((Map<String, Object>) entry.getValue()).get("request"));

                    if (!requestValidator.validate()) {
                        this.result = requestValidator.getResult();
                        return false;
                    }
                }

                if (((Map<String, Object>) entry.getValue()).containsKey("response")) {
                    responseValidator.setResponse((Map<String, Object>) ((Map<String, Object>) entry.getValue()).get("response"));

                    if (!responseValidator.validate()) {
                        this.result = responseValidator.getResult();
                        return false;
                    }
                }
            } else {
                if (!entry.getKey().equals("ignore-type") || !(entry.getValue().equals("single") || entry.getValue().equals("all"))) {
                    result.setMessage(String.format("invalid entry \"%s\" value, or value might not be allowed \"%s\"." +
                            " (only \"single\" or \"all\")", entry.getKey(), entry.getValue()))
                            .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                    return false;
                }
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
