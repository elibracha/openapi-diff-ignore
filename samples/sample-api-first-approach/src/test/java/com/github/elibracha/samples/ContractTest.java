package com.github.elibracha.samples;

import com.github.elibracha.OpenApiCompare;
import com.github.elibracha.model.ChangedOpenApi;
import com.github.elibracha.output.HtmlRender;
import com.github.elibracha.output.MarkdownRender;
import com.github.elibracha.processors.ContextProcessor;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractTest extends  AbstractContractTest {

    @Test
    public void apiContractTestV1() throws Exception {
        this.executeContractTest(
                "v1",
                ".diffignore.yaml",
                "/openapi/documentation/original.yml"
        );

    }
    @Test
    public void apiContractTestV2() throws Exception {
        this.executeContractTest(
                "v2",
                ".diffignore.yaml",
                "/openapi/documentation/original.yml"
        );
    }
}
