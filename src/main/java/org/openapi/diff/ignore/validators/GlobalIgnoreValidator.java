package org.openapi.diff.ignore.validators;

import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.GlobalIgnoreSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalIgnoreValidator<K, V> {
    private Map<K, V> ignore;
    private ValidationResult result;
    private EndpointValidator endpointValidator;

    public GlobalIgnoreValidator() {
        this.result = new ValidationResult();
        this.endpointValidator = new EndpointValidator();
    }

    public boolean validate() {
        List<String> supported = Arrays.stream(GlobalIgnoreSupport.values())
                .map(GlobalIgnoreSupport::getValue)
                .collect(Collectors.toList());


        for (Map.Entry<K, V> entry : ignore.entrySet()) {
            if (!supported.contains(entry.getKey())) {
                result.setMessage(String.format("value \"%s\" not supported", entry.getKey()))
                        .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        if (ignore.containsKey("paths")) {
            endpointValidator.setEndpoints((Map<String, Object>) ignore.get("paths"));

            boolean result = endpointValidator.validate();

            this.result = endpointValidator.getResult();

            return result;
        }

        return true;
    }

    public GlobalIgnoreValidator setIgnore(Map<K, V> ignore) {
        this.ignore = ignore;
        return this;
    }

    public ValidationResult getResult() {
        return result;
    }
}
