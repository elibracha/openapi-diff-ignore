package org.openapi.diff.ignore.ChangeTest;

import org.junit.Test;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangeTest extends AbstractChangeTest {

    @Test
    public void testContextProcessorUnchanged() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/context/diffignore.yaml",
                "changes/context/original.yaml",
                "changes/context/generated.yaml",
                "contextDiff",
                true
                );
    }

    @Test
    public void testParametersWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/parameters/diffignore.yaml",
                "changes/parameters/original.yaml",
                "changes/parameters/generated.yaml",
                "parameterWildcardDiff",
                true
        );
    }

    @Test
    public void testRequestContentMediaTypesWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/request/request_content_media_types/diffignore.yaml",
                "changes/request/request_content_media_types/original.yaml",
                "changes/request/request_content_media_types/generated.yaml",
                "requestWildMediaTypecardDiff",
                true
        );
    }

    @Test
    public void testRequestWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/request/request_wildcard/diffignore.yaml",
                "changes/request/request_wildcard/original.yaml",
                "changes/request/request_wildcard/generated.yaml",
                "requestWildcardDiff",
                true
        );
    }

    @Test
    public void testResponseWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/response/response_wildcard/diffignore_true.yaml",
                "changes/response/response_wildcard/original.yaml",
                "changes/response/response_wildcard/generated.yaml",
                "responseWildcardDiff",
                true
        );
    }

    @Test
    public void testResponseStatusWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/response/response_status_wildcard/diffignore_true.yaml",
                "changes/response/response_wildcard/original.yaml",
                "changes/response/response_wildcard/generated.yaml",
                "responseStatusWildCardTrue",
                true
        );
    }

    @Test
    public void testResponseStatusWildcardFalse() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/response/response_status_wildcard/diffignore_false.yaml",
                "changes/response/response_status_wildcard/original.yaml",
                "changes/response/response_status_wildcard/generated.yaml",
                "responseStatusWildCardFalseDiff",
                false
        );
    }

    @Test
    public void testResponseStatusSchemaWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/response/response_status_schema_wildcard/diffignore.yaml",
                "changes/response/response_status_schema_wildcard/original.yaml",
                "changes/response/response_status_schema_wildcard/generated.yaml",
                "responseStatusSchemaWildcardTrueDiff",
                true
        );
    }

    @Test
    public void testSecurityWildcardTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/security/diffignore.yaml",
                "changes/security/original.yaml",
                "changes/security/generated.yaml",
                "responseStatusSchemaWildcardTrueDiff",
                true
        );
    }

    @Test
    public void testResponseIncreasedTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/response/response_increased_true/diffignore.yaml",
                "changes/response/response_increased_true/original.yaml",
                "changes/response/response_increased_true/generated.yaml",
                "ResponseIncreasedTrue",
                true
        );
    }

    @Test
    public void testResponseIncreasedFalse() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/response/response_increased_false/diffignore.yaml",
                "changes/response/response_increased_false/original.yaml",
                "changes/response/response_increased_false/generated.yaml",
                "ResponseIncreasedFalse",
                false
        );
    }

    @Test
    public void testSecurityProcessorIncreasedTrue() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/security/increased_true/diffignore.yaml",
                "changes/security/increased_true/original.yaml",
                "changes/security/increased_true/generated.yaml",
                "securityIncreasedTrue",
                true
        );
    }

    @Test
    public void testSecurityProcessorIncreasedFalse() throws SpecificationSupportException {
        this.executeChangeTest(
                "changes/security/increased_false/diffignore.yaml",
                "changes/security/increased_false/original.yaml",
                "changes/security/increased_false/generated.yaml",
                "securityIncreasedFalse",
                false
        );
    }
}
