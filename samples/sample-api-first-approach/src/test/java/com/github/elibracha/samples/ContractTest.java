package com.github.elibracha.samples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractTest extends AbstractContractTest {

	@Test
	public void apiContractTestV1() throws Exception {
		this.executeContractTest("v1", ".diffignore.yaml", "/openapi/documentation/original.yml");

	}

	@Test
	public void apiContractTestV2() throws Exception {
		this.executeContractTest("v2", ".diffignore.yaml", "/openapi/documentation/original.yml");
	}
}
