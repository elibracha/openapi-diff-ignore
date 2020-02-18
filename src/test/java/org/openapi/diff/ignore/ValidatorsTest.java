package org.openapi.diff.ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.openapi.diff.ignore.validators.*;

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

    @Test
    public void testRequestValidationTrue() throws IOException {
        RequestValidator requestValidator = new RequestValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.request_validate_true").getPath()));

        requestValidator.setRequest(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertTrue(requestValidator.validate());
    }

    @Test
    public void testRequestValidationFalse() throws IOException {
        RequestValidator requestValidator = new RequestValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.request_validate_false").getPath()));

        requestValidator.setRequest(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertFalse(requestValidator.validate());
    }

    @Test
    public void testOperationValidationTrue() throws IOException {
        OperationValidator operationValidator = new OperationValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.operation_validate_true").getPath()));

        operationValidator.setOperations(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertTrue(operationValidator.validate());
    }

    @Test
    public void testOperationValidationFalse() throws IOException {
        OperationValidator operationValidator = new OperationValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.operation_validate_false").getPath()));

        operationValidator.setOperations(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertFalse(operationValidator.validate());
    }


    @Test
    public void testHttpMethodValidationTrue() throws IOException {
        HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.http_method_validate_true").getPath()));

        httpMethodValidator.setHttpMethod(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertTrue(httpMethodValidator.validate());
    }

    @Test
    public void testHttpMethodValidationWildCardTrue() throws IOException {
        HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.http_method_validate_wildcard_true").getPath()));

        httpMethodValidator.setHttpMethod(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertTrue(httpMethodValidator.validate());
    }

    @Test
    public void testHttpMethodValidationFalse() throws IOException {
        HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.http_method_validate_false").getPath()));

        httpMethodValidator.setHttpMethod(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertFalse(httpMethodValidator.validate());
    }

    @Test
    public void testPathValidationTrue() throws IOException {
        PathValidator pathValidator = new PathValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.path_validate_true").getPath()));

        pathValidator.setPaths(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertTrue(pathValidator.validate());
    }

    @Test
    public void testPathValidationFalse() throws IOException {
        PathValidator pathValidator = new PathValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        byte[] content = Files.readAllBytes(Paths.get(getClass().getResource("/validate/.path_validate_false").getPath()));

        pathValidator.setPaths(objectMapper.readTree(new String(content, StandardCharsets.UTF_8)));
        assertFalse(pathValidator.validate());
    }
}
