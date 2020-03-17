package com.github.elibracha.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import com.github.elibracha.models.validations.ValidationResult;
import com.github.elibracha.models.validations.enums.RequestSupport;
import com.github.elibracha.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class RequestValidator implements Validator {

    private final ValidationResult result = new ValidationResult();
    private JsonNode request;

    public boolean validate() {
        List<String> supported = Arrays.stream(RequestSupport.values())
                .map(RequestSupport::getValue)
                .collect(Collectors.toList());

        for (Iterator<Map.Entry<String, JsonNode>> it = request.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> requestScope = it.next();

            if (!supported.contains(requestScope.getKey())) {
                result.setMessage(String.format("value \"%s\" not supported in request", requestScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void setTree(JsonNode tree) {
        setRequest(tree);
    }
}
