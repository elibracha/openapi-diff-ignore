package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Iterator;
import java.util.Map;

@Data
public class PathValidator implements Validator {

    private static final String ENDPOINT_PATTERN = "(\\/[a-zA-Z{}1-9$*\\/]+)";

    private final OperationValidator operationValidator = new OperationValidator();
    private final ValidationResult result = new ValidationResult();
    private JsonNode paths;

    public boolean validate() {

        for (Iterator<Map.Entry<String, JsonNode>> it = paths.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> pathScope = it.next();

            if (!pathScope.getKey().matches(ENDPOINT_PATTERN)) {
                result.setMessage(String.format("value %s not a valid endpoint", pathScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            operationValidator.setOperations(pathScope.getValue());

            if (!operationValidator.validate()) {
                this.result.setMessage(operationValidator.getResult().getMessage());
                this.result.setValidationStatus(operationValidator.getResult().getValidationStatus());
                return false;
            }
        }

        return true;
    }
}
