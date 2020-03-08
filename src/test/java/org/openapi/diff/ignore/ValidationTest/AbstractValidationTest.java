package org.openapi.diff.ignore.ValidationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractValidationTest {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    protected void executeValidationTest(Validator validator, String filePath, boolean assertTrue) throws JsonProcessingException {
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        validator.setTree(objectMapper.readTree(content));

        boolean rs = validator.validate();

        try {
            if (assertTrue)
                assertTrue(rs);
            else {
                assertFalse(rs);
            }
        } catch (AssertionError e){
            if(validator.getResult().getMessage() != null)
                log.error(String.format("%s: %s - %s",
                        validator.getResult().getValidationStatus().getStatus(),
                        validator.getResult().getValidationStatus().getMessage(),
                        validator.getResult().getMessage()));
            throw e;
        }

    }

}
