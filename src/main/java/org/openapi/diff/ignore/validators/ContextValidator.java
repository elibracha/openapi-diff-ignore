package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.ContextSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ContextValidator implements Validator {
    private final ValidationResult result = new ValidationResult();
    private final PathValidator pathValidator = new PathValidator();
    private JsonNode ignore;

    public boolean validate() {
        List<String> supported = Arrays.stream(ContextSupport.values())
                .map(ContextSupport::getValue)
                .collect(Collectors.toList());


        for (Iterator<Map.Entry<String, JsonNode>> it = ignore.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> contextScope = it.next();

            if (!supported.contains(contextScope.getKey())) {
                result.setMessage(String.format("value \"%s\" not supported", contextScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        if (ignore.get("version") != null) {
            if (!SpecConstants.VERSIONS.contains(ignore.get("version").asText())) {
                result.setMessage(String.format("version not supported \"%s\"", ignore.get("version")));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        } else {
            result.setMessage("version spec must be present in ignore file.");
            result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
            return false;
        }

        if (ignore.get("paths") != null) {
            pathValidator.setPaths(ignore.get("paths"));
            boolean r = pathValidator.validate();

            result.setValidationStatus(pathValidator.getResult().getValidationStatus());
            result.setMessage(pathValidator.getResult().getMessage());

            return r;
        }

        return true;
    }

}
