package org.openapi.diff.ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.openapi.diff.ignore.validators.ResponseValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorsTest {

    @Test
    public void testResponseValidationTrue() throws IOException {
        ResponseValidator responseValidator = new ResponseValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.response_validate_true").getPath()));

        responseValidator.setResponse(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertTrue(responseValidator.validate());
    }

    @Test
    public void testResponseValidationFalse() throws IOException {
        ResponseValidator responseValidator = new ResponseValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.response_validate_false").getPath()));

        responseValidator.setResponse(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertFalse(responseValidator.validate());
    }
}
