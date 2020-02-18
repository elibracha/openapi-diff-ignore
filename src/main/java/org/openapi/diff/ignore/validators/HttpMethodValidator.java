package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.HttpMethodSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class HttpMethodValidator {

    private final OperationValidator operationValidator = new OperationValidator();
    private final ValidationResult result = new ValidationResult();
    private JsonNode httpMethod;

    public boolean validate() {
        List<String> supported = Arrays.stream(HttpMethodSupport.values())
                .map(HttpMethodSupport::getValue)
                .collect(Collectors.toList());


        for (Iterator<Map.Entry<String, JsonNode>> it = httpMethod.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> httpMethodScope = it.next();

            if (!supported.contains(httpMethodScope.getKey()) && !httpMethodScope.getKey().equals("$")) {
                result.setMessage(String.format("the method \"%s\" not a valid entry in http method", httpMethodScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            operationValidator.setOperations(httpMethodScope.getValue());

            if (!operationValidator.validate()) {
                this.result.setMessage(operationValidator.getResult().getMessage());
                this.result.setValidationStatus(operationValidator.getResult().getValidationStatus());
                return false;
            }
        }

        return true;
    }
}
