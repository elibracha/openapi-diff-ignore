package org.openapi.diff.ignore;

import com.qdesrame.openapi.diff.OpenApiCompare;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.output.HtmlRender;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.processors.ContextProcessor;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractChangeTest {

    protected void executeChangeTest(String ignorePath, String originalPath, String generatedPath, String outputPath, boolean assertTrue) throws SpecificationSupportException{
        ContextProcessor contextProcessor = new ContextProcessor(
                getClass().getClassLoader().getResource(ignorePath).getFile()
        );

        ChangedOpenApi changedOpenApi = OpenApiCompare.fromLocations(originalPath, generatedPath);

        ChangedOpenApi changedOpenApiAfter = contextProcessor.process(changedOpenApi);


        String html =
                new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                        .render(changedOpenApiAfter);
        try {
            FileWriter fw = new FileWriter(String.format("target/%s.html", outputPath));
            fw.write(html);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(assertTrue)
            assertTrue(changedOpenApi.isUnchanged());
        else{
            assertFalse(changedOpenApi.isUnchanged());
        }
    }

}
