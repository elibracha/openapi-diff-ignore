package org.openapi.diff.ignore;

import com.qdesrame.openapi.diff.OpenApiCompare;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.output.HtmlRender;
import org.junit.Test;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.processors.ContextProcessor;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangeTest {

    private final String OPENAPI_GENERATED_PETSTORE = "petstore_v3_generated.yaml";
    private final String OPENAPI_ORIGINAL_PETSTORE = "petstore_v3_original.yaml";


    @Test
    public void testContextProcessorUnchange() throws SpecificationSupportException {
        ContextProcessor contextProcessor = new ContextProcessor(
                getClass().getClassLoader().getResource("petstore_v3_diffignore.yaml").getFile()
        );

        ChangedOpenApi changedOpenApi = OpenApiCompare.fromLocations(OPENAPI_ORIGINAL_PETSTORE, OPENAPI_GENERATED_PETSTORE);

        ChangedOpenApi changedOpenApiAfter = contextProcessor.process(changedOpenApi);


        String html =
                new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                        .render(changedOpenApiAfter);
        try {
            FileWriter fw = new FileWriter("target/contextDiff.html");
            fw.write(html);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(changedOpenApi.isUnchanged());
    }
    @Test
    public void testResponseWildcardTrue() throws SpecificationSupportException {
        ContextProcessor contextProcessor = new ContextProcessor(
                getClass().getClassLoader().getResource("changes/response_wildcard/diffignore_true.yaml").getFile()
        );

        ChangedOpenApi changedOpenApi = OpenApiCompare.fromLocations("changes/response_wildcard/original.yaml", "changes/response_wildcard/generated.yaml");

        ChangedOpenApi changedOpenApiAfter = contextProcessor.process(changedOpenApi);

        String html =
                new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                        .render(changedOpenApiAfter);
        try {
            FileWriter fw = new FileWriter("target/responseWildcardDiff.html");
            fw.write(html);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(changedOpenApi.isUnchanged());
    }
    @Test
    public void testResponseStatusWildcardTrue() throws SpecificationSupportException {
        ContextProcessor contextProcessor = new ContextProcessor(
                getClass().getClassLoader().getResource("changes/response_status_wildcard/diffignore_true.yaml").getFile()
        );

        ChangedOpenApi changedOpenApi = OpenApiCompare.fromLocations("changes/response_wildcard/original.yaml", "changes/response_wildcard/generated.yaml");

        ChangedOpenApi changedOpenApiAfter = contextProcessor.process(changedOpenApi);

        String html =
                new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                        .render(changedOpenApiAfter);
        try {
            FileWriter fw = new FileWriter("target/responseStatusWildCardTrue.html");
            fw.write(html);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(changedOpenApi.isUnchanged());
    }
    @Test
    public void testResponseStatusWildcardFalse() throws SpecificationSupportException {
        ContextProcessor contextProcessor = new ContextProcessor(
                getClass().getClassLoader().getResource("changes/response_status_wildcard/diffignore_false.yaml").getFile()
        );

        ChangedOpenApi changedOpenApi = OpenApiCompare.fromLocations("changes/response_status_wildcard/original.yaml", "changes/response_status_wildcard/generated.yaml");

        ChangedOpenApi changedOpenApiAfter = contextProcessor.process(changedOpenApi);

        String html =
                new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                        .render(changedOpenApiAfter);
        try {
            FileWriter fw = new FileWriter("target/responseStatusWildCardFalseDiff.html");
            fw.write(html);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertFalse(changedOpenApi.isUnchanged());
    }
}
