package com.github.elibracha.ChangeTest;

import com.github.elibracha.OpenApiCompare;
import com.github.elibracha.model.ChangedOpenApi;
import com.github.elibracha.output.HtmlRender;
import com.github.elibracha.exceptions.SpecificationSupportException;
import com.github.elibracha.processors.ContextProcessor;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractChangeTest {

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