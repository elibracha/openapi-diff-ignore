package org.openapi.diff.ignore;

import com.qdesrame.openapi.diff.OpenApiCompare;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.output.HtmlRender;
import org.junit.Test;
import org.openapi.diff.ignore.models.IgnoreOpenApi;
import org.openapi.diff.ignore.processors.ApplyIgnorePostProcessor;
import org.openapi.diff.ignore.processors.IgnoreProcessor;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class IgnoreParserTest {

    private final String OPENAPI_GENERATED_PETSTORE = "petstore_v3_generated.yaml";
    private final String OPENAPI_ORIGINAL_PETSTORE = "petstore_v3_orignal.yaml";

    @Test
    public void test() {
        IgnoreProcessor parser = new IgnoreProcessor();

        IgnoreOpenApi ignoreOpenApi = parser.processIgnore();
        ChangedOpenApi changedOpenApi = OpenApiCompare.fromLocations(OPENAPI_ORIGINAL_PETSTORE, OPENAPI_GENERATED_PETSTORE);

        ApplyIgnorePostProcessor applyIgnorePostProcessor = new ApplyIgnorePostProcessor(changedOpenApi, ignoreOpenApi);
        applyIgnorePostProcessor.applyIgnore();

        String html =
                new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                        .render(changedOpenApi);
        try {
            FileWriter fw = new FileWriter("target/testDiff.html");
            fw.write(html);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(changedOpenApi.isUnchanged());
    }
}
