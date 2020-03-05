package org.openapi.diff.ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.openapi.diff.ignore.validators.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorsTest {

    @Test
    public void testVersionValidationTrue() throws IOException {
        ContextValidator contextValidator = new ContextValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.context_version_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        contextValidator.setIgnore(objectMapper.readTree(content));
        assertTrue(contextValidator.validate());
    }


    @Test
    public void testVersionValidationFalse() throws IOException {
        ContextValidator contextValidator = new ContextValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.context_version_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        contextValidator.setIgnore(objectMapper.readTree(content));
        assertFalse(contextValidator.validate());
    }

    @Test
    public void testResponseValidationTrue() throws IOException {
        ResponseValidator responseValidator = new ResponseValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.response_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        responseValidator.setResponse(objectMapper.readTree(content));
        assertTrue(responseValidator.validate());
    }

    @Test
    public void testResponseValidationFalse() throws IOException {
        ResponseValidator responseValidator = new ResponseValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.response_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        responseValidator.setResponse(objectMapper.readTree(content));
        assertFalse(responseValidator.validate());
    }

    @Test
    public void testRequestValidationTrue() throws IOException {
        RequestValidator requestValidator = new RequestValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.request_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        requestValidator.setRequest(objectMapper.readTree(content));
        assertTrue(requestValidator.validate());
    }

    @Test
    public void testRequestValidationFalse() throws IOException {
        RequestValidator requestValidator = new RequestValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.request_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        requestValidator.setRequest(objectMapper.readTree(content));
        assertFalse(requestValidator.validate());
    }

    @Test
    public void testOperationValidationTrue() throws IOException {
        OperationValidator operationValidator = new OperationValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.operation_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        operationValidator.setOperations(objectMapper.readTree(content));
        assertTrue(operationValidator.validate());
    }

    @Test
    public void testOperationValidationFalse() throws IOException {
        OperationValidator operationValidator = new OperationValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.operation_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        operationValidator.setOperations(objectMapper.readTree(content));
        assertFalse(operationValidator.validate());
    }


    @Test
    public void testHttpMethodValidationTrue() throws IOException {
        HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.http_method_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        httpMethodValidator.setHttpMethod(objectMapper.readTree(content));
        assertTrue(httpMethodValidator.validate());
    }

    @Test
    public void testHttpMethodValidationWildCardTrue() throws IOException {
        HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.http_method_validate_wildcard_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        httpMethodValidator.setHttpMethod(objectMapper.readTree(content));
        assertTrue(httpMethodValidator.validate());
    }

    @Test
    public void testHttpMethodValidationFalse() throws IOException {
        HttpMethodValidator httpMethodValidator = new HttpMethodValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.http_method_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        httpMethodValidator.setHttpMethod(objectMapper.readTree(content));
        assertFalse(httpMethodValidator.validate());
    }

    @Test
    public void testPathValidationTrue() throws IOException {
        PathValidator pathValidator = new PathValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.path_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        pathValidator.setPaths(objectMapper.readTree(content));
        assertTrue(pathValidator.validate());
    }

    @Test
    public void testPathValidationFalse() throws IOException {
        PathValidator pathValidator = new PathValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.path_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        pathValidator.setPaths(objectMapper.readTree(content));
        assertFalse(pathValidator.validate());
    }

    @Test
    public void testPathValidationWildCardTrue() throws IOException {
        PathValidator pathValidator = new PathValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.path_validate_wildcard_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        pathValidator.setPaths(objectMapper.readTree(content));
        assertTrue(pathValidator.validate());
    }

    @Test
    public void testContextValidationTrue() throws IOException {
        ContextValidator contextValidator = new ContextValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.context_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());
        contextValidator.setIgnore(objectMapper.readTree(content));
        assertTrue(contextValidator.validate());
    }

    @Test
    public void testContextValidationFalse() throws IOException {
        ContextValidator contextValidator = new ContextValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        InputStream is = classloader.getResourceAsStream("validate/.context_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());
        contextValidator.setIgnore(objectMapper.readTree(content));
        assertFalse(contextValidator.validate());
    }

    @Test
    public void testParamsValidationTrue() throws IOException {
        ParamsValidator paramsValidator = new ParamsValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.param_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());
        paramsValidator.setParams(objectMapper.readTree(content));
        assertTrue(paramsValidator.validate());
    }

    @Test
    public void testParamsvalidationFalse() throws IOException {
        ParamsValidator paramsValidator = new ParamsValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        InputStream is = classloader.getResourceAsStream("validate/.param_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());
        paramsValidator.setParams(objectMapper.readTree(content));
        assertFalse(paramsValidator.validate());
    }

    @Test
    public void testParamsValidationWildcardTrue() throws IOException {
        ParamsValidator paramsValidator = new ParamsValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.param_validate_wildcare_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        paramsValidator.setParams(objectMapper.readTree(content));
        assertTrue(paramsValidator.validate());
    }

    @Test
    public void testSecurityValidationTrue() throws IOException {
        SecurityValidator securityValidator = new SecurityValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.security_validate_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());
        securityValidator.setSecurity(objectMapper.readTree(content));
        assertTrue(securityValidator.validate());
    }

    @Test
    public void testSecurityValidationFalse() throws IOException {
        SecurityValidator securityValidator = new SecurityValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        InputStream is = classloader.getResourceAsStream("validate/.security_validate_false");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());
        securityValidator.setSecurity(objectMapper.readTree(content));
        assertFalse(securityValidator.validate());
    }

    @Test
    public void testSecurityValidationWildcardTrue() throws IOException {
        SecurityValidator securityValidator = new SecurityValidator();
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("validate/.security_validate_wildcard_true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = reader.lines().map(s -> s + System.lineSeparator()).collect(Collectors.joining());

        securityValidator.setSecurity(objectMapper.readTree(content));
        assertTrue(securityValidator.validate());
    }
}
