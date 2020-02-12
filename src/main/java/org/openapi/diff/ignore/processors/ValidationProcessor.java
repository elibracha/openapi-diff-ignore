package org.openapi.diff.ignore.processors;

import org.openapi.diff.ignore.validators.GlobalIgnoreValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;

public class ValidationProcessor<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private GlobalIgnoreValidator<K, V> globalIgnoreValidator;
    private
    public ValidationProcessor() {
        this.globalIgnoreValidator = new GlobalIgnoreValidator<>();
    }

    public boolean validate(Map<K, V> ignore) {
        if (globalIgnoreValidator.setIgnore(ignore).validate()) {
            return true;
        }

        log.error(String.format("%s: %s - %s",
                globalIgnoreValidator.getResult().getValidationStatus().getStatus(),
                globalIgnoreValidator.getResult().getValidationStatus().getMessage(),
                globalIgnoreValidator.getResult().getMessage()));


        return false;
    }
}
