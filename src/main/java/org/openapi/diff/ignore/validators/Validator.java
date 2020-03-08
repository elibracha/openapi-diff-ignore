package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.models.validations.ValidationResult;

public interface Validator {

    boolean validate();
    void setTree(JsonNode tree);
    ValidationResult getResult();
}
