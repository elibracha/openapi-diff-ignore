package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
public class SecurityValidator implements Validator {


    private final ValidationResult result = new ValidationResult();
    private JsonNode security;

    @Override
    public boolean validate() {
        List<Map.Entry<String, JsonNode>> targetStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(security.fields(), Spliterator.ORDERED),
                true).collect(Collectors.toList());
        for (Map.Entry<String, JsonNode> entry : targetStream) {
            if (!(entry.getValue() instanceof ArrayNode) && !entry.getValue().asText().equals("$")) {
                result.setMessage(String.format("the method \"%s\" not a valid object security method", entry.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTree(JsonNode tree) {
        setSecurity(tree);
    }
}
