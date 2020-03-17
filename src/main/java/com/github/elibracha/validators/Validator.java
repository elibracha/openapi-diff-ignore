package com.github.elibracha.validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.models.validations.ValidationResult;

public interface Validator {

    boolean validate();
    void setTree(JsonNode tree);
    ValidationResult getResult();
}
