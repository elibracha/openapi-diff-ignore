package com.github.elibracha.samples;

import com.github.elibracha.OpenApiCompare;
import com.github.elibracha.model.ChangedOpenApi;
import com.github.elibracha.output.HtmlRender;
import com.github.elibracha.output.MarkdownRender;
import com.github.elibracha.processors.ContextProcessor;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractContractTest {

	private Path resourceDirectory = Paths.get("src", "test", "resources");

	@Autowired
	private MockMvc mockMvc;

	protected void executeContractTest(String version, String ignorePath, String originalPath) throws Exception {
		ContextProcessor contextProcessor = new ContextProcessor(
				getClass().getClassLoader().getResource(ignorePath).getFile());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs/" + version).accept(MediaType.APPLICATION_JSON))
				.andDo((result) -> {
					String swaggerJsonString = result.getResponse().getContentAsString();
					FileUtils.writeStringToFile(new File(resourceDirectory + "/openapi/documentation/generated.json"),
							swaggerJsonString, Charset.defaultCharset());
				});

		ChangedOpenApi diff = OpenApiCompare.fromLocations(resourceDirectory + originalPath,
				resourceDirectory + "/openapi/documentation/generated.json");
		contextProcessor.process(diff);

		renderMarkDown(diff);
		renderHtml(diff);

		Assert.assertEquals(0, diff.getChangedOperations().size());
		Assert.assertEquals(0, diff.getMissingEndpoints().size());
		Assert.assertEquals(0, diff.getDeprecatedEndpoints().size());
	}

	private void renderMarkDown(ChangedOpenApi diff) throws IOException {
		String render = new MarkdownRender().render(diff);
		FileWriter fw = new FileWriter(resourceDirectory + "/openapi/diff/api_diff-"
				+ diff.getNewSpecOpenApi().getInfo().getVersion() + ".md");
		fw.write(render);
		fw.close();
	}

	private void renderHtml(ChangedOpenApi diff) throws IOException {
		String render = new HtmlRender().render(diff);
		FileWriter fw = new FileWriter(resourceDirectory + "/openapi/diff/api_diff-"
				+ diff.getNewSpecOpenApi().getInfo().getVersion() + ".html");
		fw.write(render);
		fw.close();
	}
}