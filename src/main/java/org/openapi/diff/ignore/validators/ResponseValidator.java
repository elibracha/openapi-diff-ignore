package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Iterator;
import java.util.Map;

@Data
public class ResponseValidator implements Validator {

    private ValidationResult result = new ValidationResult();
    private JsonNode response;

    public boolean validate() {
        for (Iterator<Map.Entry<String, JsonNode>> it = response.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> responseScope = it.next();

            if (!(StringUtils.isNumeric(responseScope.getKey()) || responseScope.getKey().equals("default"))) {
                result.setMessage(String.format("value \"%s\" not supported in response status", responseScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTree(JsonNode tree) {
        setResponse(tree);
    }
}
