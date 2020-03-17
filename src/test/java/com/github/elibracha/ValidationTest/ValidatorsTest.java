package com.github.elibracha.ValidationTest;

import com.github.elibracha.validators.*;
import org.junit.Test;
import com.github.elibracha.validators.*;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorsTest extends AbstractValidationTest{

    @Test
    public void testVersionValidationTrue() throws IOException {
        executeValidationTest(
                new ContextValidator(),
                "validate/.context_version_validate_true",
                true
        );
    }

    @Test
    public void testVersionValidationFalse() throws IOException {
        executeValidationTest(
                new ContextValidator(),
                "validate/.context_version_validate_false",
                false
        );
    }

    @Test
    public void testResponseValidationTrue() throws IOException {
        executeValidationTest(
                new ResponseValidator(),
                "validate/.response_validate_true",
                true
        );
    }

    @Test
    public void testResponseValidationFalse() throws IOException {
        executeValidationTest(
                new ResponseValidator(),
                "validate/.response_validate_false",
                false
        );
    }

    @Test
    public void testRequestValidationTrue() throws IOException {
        executeValidationTest(
                new RequestValidator(),
                "validate/.request_validate_true",
                true
        );
    }

    @Test
    public void testRequestValidationFalse() throws IOException {
        executeValidationTest(
                new RequestValidator(),
                "validate/.request_validate_false",
                false
        );
    }

    @Test
    public void testOperationValidationTrue() throws IOException {
        executeValidationTest(
                new OperationValidator(),
                "validate/.operation_validate_true",
                true
        );
    }

    @Test
    public void testOperationValidationFalse() throws IOException {
        executeValidationTest(
                new OperationValidator(),
                "validate/.operation_validate_false",
                false
        );
    }

    @Test
    public void testHttpMethodValidationTrue() throws IOException {
        executeValidationTest(
                new HttpMethodValidator(),
                "validate/.http_method_validate_true",
                true
        );
    }

    @Test
    public void testHttpMethodValidationWildCardTrue() throws IOException {
        executeValidationTest(
                new HttpMethodValidator(),
                "validate/.http_method_validate_wildcard_true",
                true
        );
    }

    @Test
    public void testHttpMethodValidationFalse() throws IOException {
        executeValidationTest(
                new HttpMethodValidator(),
                "validate/.http_method_validate_false",
                false
        );
    }

    @Test
    public void testPathValidationTrue() throws IOException {
        executeValidationTest(
                new PathValidator(),
                "validate/.path_validate_true",
                true
        );
    }

    @Test
    public void testPathValidationFalse() throws IOException {
        executeValidationTest(
                new PathValidator(),
                "validate/.path_validate_false",
                false
        );
    }

    @Test
    public void testPathValidationWildCardTrue() throws IOException {
        executeValidationTest(
                new PathValidator(),
                "validate/.path_validate_wildcard_true",
                true
        );
    }

    @Test
    public void testContextValidationTrue() throws IOException {
        executeValidationTest(
                new ContextValidator(),
                "validate/.context_validate_true",
                true
        );
    }

    @Test
    public void testContextValidationFalse() throws IOException {
        executeValidationTest(
                new ContextValidator(),
                "validate/.context_validate_false",
                false
        );
    }

    @Test
    public void testParamsValidationTrue() throws IOException {
        executeValidationTest(
                new ParamsValidator(),
                "validate/.param_validate_true",
                true
        );
    }

    @Test
    public void testParamsvalidationFalse() throws IOException {
        executeValidationTest(
                new ParamsValidator(),
                "validate/.param_validate_false",
                false
        );
    }

    @Test
    public void testParamsValidationWildcardTrue() throws IOException {
        executeValidationTest(
                new ParamsValidator(),
                "validate/.param_validate_wildcare_true",
                true
        );
    }

    @Test
    public void testSecurityValidationTrue() throws IOException {
        executeValidationTest(
                new SecurityValidator(),
                "validate/.security_validate_true",
                true
        );
    }

    @Test
    public void testSecurityValidationFalse() throws IOException {
        executeValidationTest(
                new SecurityValidator(),
                "validate/.security_validate_false",
                false
        );
    }

    @Test
    public void testSecurityValidationWildcardTrue() throws IOException {
        executeValidationTest(
                new SecurityValidator(),
                "validate/.security_validate_wildcard_true",
                true
        );
    }
}
