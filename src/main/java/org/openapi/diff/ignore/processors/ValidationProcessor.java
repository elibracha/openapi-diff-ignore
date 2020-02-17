package org.openapi.diff.ignore.processors;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.openapi.diff.ignore.validators.ContextIgnoreValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@Data
public class ValidationProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final ContextIgnoreValidator contextIgnoreValidator = new ContextIgnoreValidator();

    public boolean validate(JsonNode ignore) {
        contextIgnoreValidator.setIgnore(ignore);

        if (contextIgnoreValidator.validate()) {
            return true;
        }

        log.error(String.format("%s: %s - %s",
                contextIgnoreValidator.getResult().getValidationStatus().getStatus(),
                contextIgnoreValidator.getResult().getValidationStatus().getMessage(),
                contextIgnoreValidator.getResult().getMessage()));


        return false;
    }
}
