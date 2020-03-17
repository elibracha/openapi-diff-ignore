package com.github.elibracha.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.validators.ContextValidator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@Data
public class ValidationProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final ContextValidator contextIgnoreValidator = new ContextValidator();

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
