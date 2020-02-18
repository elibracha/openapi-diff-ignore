package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.OperationSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class OperationValidator implements Validator {

    private final ValidationResult result = new ValidationResult();
    private final ResponseValidator responseValidator = new ResponseValidator();
    private final RequestValidator requestValidator = new RequestValidator();
    private JsonNode operations;

    public boolean validate() {

        List<String> supported = Arrays.stream(OperationSupport.values())
                .map(OperationSupport::getValue)
                .collect(Collectors.toList());

        for (Iterator<Map.Entry<String, JsonNode>> it = operations.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> operationScope = it.next();

            if (!supported.contains(operationScope.getKey()) && !operationScope.getKey().equals("$")) {
                result.setMessage(String.format("the value \"%s\" is not a valid entry in operation", operationScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            if (operationScope.getValue().get("request") != null) {
                requestValidator.setRequest(operationScope.getValue().get("request"));
                boolean r = requestValidator.validate();
                if (!r) {
                    result.setMessage(requestValidator.getResult().getMessage());
                    result.setValidationStatus(requestValidator.getResult().getValidationStatus());
                    return false;
                }
            }
            if (operationScope.getValue().get("response") != null) {
                responseValidator.setResponse(operationScope.getValue().get("response"));
                boolean r = responseValidator.validate();
                if (!r) {
                    result.setMessage(responseValidator.getResult().getMessage());
                    result.setValidationStatus(responseValidator.getResult().getValidationStatus());
                    return false;
                }
            }
        }
        return true;
    }
}
