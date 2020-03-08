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

    private final HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
    private final ValidationResult result = new ValidationResult();
    private JsonNode paths;

    public boolean validate() {

        for (Iterator<Map.Entry<String, JsonNode>> it = paths.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> pathScope = it.next();
            if (pathScope.getKey().contains(",")) {
                for (String path : pathScope.getKey().split(","))
                    if (!path.trim().equals("$") && !path.trim().matches(ENDPOINT_PATTERN)) {
                        result.setMessage(String.format("value \"%s\" not a valid path", path.trim()));
                        result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                        return false;
                    }

            } else if (!pathScope.getKey().equals("$") && !pathScope.getKey().matches(ENDPOINT_PATTERN)) {
                result.setMessage(String.format("value \"%s\" not a valid path", pathScope.getKey().trim()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            httpMethodValidator.setHttpMethod(pathScope.getValue());

            if (!httpMethodValidator.validate()) {
                this.result.setMessage(httpMethodValidator.getResult().getMessage());
                this.result.setValidationStatus(httpMethodValidator.getResult().getValidationStatus());
                return false;
            }
        }

        return true;
    }

    @Override
    public void setTree(JsonNode tree) {
        setPaths(tree);
    }


}
