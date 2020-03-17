package org.openapi.diff.ignore.samples;

import com.qdesrame.openapi.diff.OpenApiCompare;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.output.HtmlRender;
import com.qdesrame.openapi.diff.output.MarkdownRender;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainApp.class)
public class ContractTest {

    private Path resourceDirectory = Paths.get("src", "test", "resources");

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void apiContractTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo((result) -> {
                    String swaggerJsonString = result.getResponse().getContentAsString();
                    FileUtils.writeStringToFile(new File(resourceDirectory + "/swagger/documentation/openapi_from_code.json"), swaggerJsonString, Charset.defaultCharset());
                });


        ChangedOpenApi diff = OpenApiCompare.fromLocations(resourceDirectory + "/swagger/documentation/openapi.yml", resourceDirectory + "/swagger/documentation/openapi_from_code.json");
        renderMarkDown(diff);
        renderHtml(diff);
        Assert.assertEquals(0, diff.getChangedOperations().size());
        Assert.assertEquals(0, diff.getMissingEndpoints().size());
        Assert.assertEquals(0, diff.getDeprecatedEndpoints().size());
    }

    private void renderMarkDown(ChangedOpenApi diff) throws IOException {
        String render = new MarkdownRender().render(diff);
        FileWriter fw = new FileWriter(
                resourceDirectory + "/swagger/diff/api_diff-" + diff.getNewSpecOpenApi().getInfo().getVersion() + ".md");
        fw.write(render);
        fw.close();
    }

    private void renderHtml(ChangedOpenApi diff) throws IOException {
        String render = new HtmlRender().render(diff);
        FileWriter fw = new FileWriter(
                resourceDirectory + "/swagger/diff/api_diff-" + diff.getNewSpecOpenApi().getInfo().getVersion() + ".html");
        fw.write(render);
        fw.close();
    }

}
