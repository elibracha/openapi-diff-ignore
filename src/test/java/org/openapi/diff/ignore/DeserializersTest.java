package org.openapi.diff.ignore;

import com.qdesrame.openapi.diff.OpenApiCompare;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.output.HtmlRender;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.junit.Test;
import org.openapi.diff.ignore.models.OpenApiIgnore;
import org.openapi.diff.ignore.models.ignore.*;
import org.openapi.diff.ignore.processors.ContextProcessor;
import org.openapi.diff.ignore.processors.OpenApiPreprocessor;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeserializersTest {

    private final String OPENAPI_GENERATED_PETSTORE = "petstore_v3_generated.yaml";
    private final String OPENAPI_ORIGINAL_PETSTORE = "petstore_v3_original.yaml";


    @Test
    public void test() {
        ContextProcessor parser = new ContextProcessor(
                getClass().getClassLoader().getResource("petstore_v3_diffignore").getFile()
        );

        OpenAPI openApiOriginal = new OpenAPIV3Parser().read(getClass().getClassLoader().getResource(OPENAPI_ORIGINAL_PETSTORE).getFile());
        OpenAPI openApiOGenerated = new OpenAPIV3Parser().read(getClass().getClassLoader().getResource(OPENAPI_GENERATED_PETSTORE).getFile());

        OpenApiIgnore ignoreOpenApi = parser.processIgnore();

        OpenApiPreprocessor openApiPreprocessor = new OpenApiPreprocessor(openApiOriginal, openApiOGenerated, ignoreOpenApi);
        openApiPreprocessor.process();


        ChangedOpenApi changedOpenApi = OpenApiCompare.fromSpecifications(openApiOriginal, openApiOGenerated);
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

    @Test
    public void testContextDeserialization() {
        ContextProcessor parser = new ContextProcessor(
                getClass().getClassLoader().getResource(".context").getFile()
        );

        OpenApiIgnore ignoreOpenApi = new OpenApiIgnore();
        ContextIgnore contextIgnore = new ContextIgnore();
        PathsIgnore pathsIgnore = new PathsIgnore();

        HttpMethodIgnore httpMethodIgnore = new HttpMethodIgnore();

        OperationIgnore operationIgnorePut = new OperationIgnore();
        OperationIgnore operationIgnoreGet = new OperationIgnore();
        OperationIgnore operationIgnorePost = new OperationIgnore();

        ParametersIgnore parametersIgnoreGet = new ParametersIgnore();

        ResponseIgnore responseIgnorePost = new ResponseIgnore();
        RequestIgnore requestIgnorePost = new RequestIgnore();
        SecurityIgnore securityIgnorePost = new SecurityIgnore();
        ParametersIgnore parametersIgnorePost = new ParametersIgnore();

        StatusIgnore statusIgnore = new StatusIgnore();

        SecurityProperty securityPropertyPetAUth = new SecurityProperty();
        SecurityProperty securityPropertyRandom = new SecurityProperty();

        securityPropertyRandom.setProperties(Collections.singletonList("write:random"));
        securityPropertyPetAUth.setProperties(Arrays.asList("write:pets", "write:pests", "read:pets"));

        Map<String, SecurityProperty> security = new HashMap<String, SecurityProperty>() {
            {
                put("petstore_auth", securityPropertyPetAUth);
                put("random", securityPropertyRandom);
            }
        };
        securityIgnorePost.setSecurity(security);

        Content contentResponse = new Content();

        ContentSchema contentSchemaResponseJson = new ContentSchema();
        ContentProperties contentPropertiesResponseJson = new ContentProperties();
        contentPropertiesResponseJson.setProperties(Collections.singletonList("petId"));
        contentSchemaResponseJson.setSchema(contentPropertiesResponseJson);

        ContentSchema contentSchemaResponseXml = new ContentSchema();
        ContentProperties contentPropertiesResponseXml = new ContentProperties();
        contentPropertiesResponseXml.setProperties(Collections.singletonList("orderId"));
        contentSchemaResponseXml.setSchema(contentPropertiesResponseXml);

        Map<String, ContentSchema> response = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaResponseJson);
                put("application/xml", contentSchemaResponseXml);
            }
        };

        contentResponse.setContent(response);

        Map<String, Content> status = new HashMap<String, Content>() {
            {
                put("200", contentResponse);
            }
        };

        statusIgnore.setStatus(status);
        responseIgnorePost.setResponse(statusIgnore);

        Content contentRequest = new Content();

        ContentSchema contentSchemaRequestJson = new ContentSchema();
        ContentProperties contentPropertiesRequestJson = new ContentProperties();
        contentPropertiesRequestJson.setProperties(Collections.singletonList("petId"));
        contentSchemaRequestJson.setSchema(contentPropertiesRequestJson);

        ContentSchema contentSchemaRequestUrlEncoded = new ContentSchema();
        ContentProperties contentPropertiesRequestUrlEncoded = new ContentProperties();
        contentPropertiesRequestUrlEncoded.setProperties(Collections.singletonList("orderId"));
        contentSchemaRequestUrlEncoded.setSchema(contentPropertiesRequestUrlEncoded);

        Map<String, ContentSchema> request = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaRequestJson);
                put("application/x-www-form-urlencoded", contentSchemaRequestUrlEncoded);
            }
        };

        contentRequest.setContent(request);
        requestIgnorePost.setRequest(contentRequest);

        parametersIgnorePost.setParameters(Arrays.asList("username", "password"));

        parametersIgnoreGet.setParameters(Arrays.asList("username", "password"));

        operationIgnoreGet.setParameters(parametersIgnoreGet);

        operationIgnorePost.setParameters(parametersIgnorePost);
        operationIgnorePost.setSecurity(securityIgnorePost);
        operationIgnorePost.setResponse(responseIgnorePost);
        operationIgnorePost.setRequest(requestIgnorePost);

        operationIgnorePut.setIgnoreAll(true);

        httpMethodIgnore.setPost(operationIgnorePost);
        httpMethodIgnore.setPut(operationIgnorePut);
        httpMethodIgnore.setGet(operationIgnoreGet);


        Map<String, HttpMethodIgnore> paths = new HashMap<String, HttpMethodIgnore>() {
            {
                put("/**", httpMethodIgnore);
            }
        };

        pathsIgnore.setPaths(paths);

        contextIgnore.setPaths(pathsIgnore);
        contextIgnore.setInfo("this is a sample ignore file to silence changes in openapi-diff");
        contextIgnore.setProject("sample-service");
        contextIgnore.setVersion("1.0.0");

        OpenApiIgnore ignoreOpenApiContext = parser.processIgnore();

        ignoreOpenApi.setIgnore(contextIgnore);
        ignoreOpenApi.setValidIgnore(true);

        assertEquals(ignoreOpenApiContext, ignoreOpenApi);
    }

    @Test
    public void testSecurityDeserialization() throws FileNotFoundException {
        Map<String, Object> result = loadMap(".security");

        SecurityIgnore securityIgnore = new SecurityIgnore();
        SecurityIgnore securityIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(result, SecurityIgnore.class);

        SecurityProperty securityPropertyPetAUth = new SecurityProperty();
        SecurityProperty securityPropertyRandom = new SecurityProperty();

        securityPropertyRandom.setProperties(Collections.singletonList("write:random"));
        securityPropertyPetAUth.setProperties(Arrays.asList("write:pets", "write:pests", "read:pets"));

        Map<String, SecurityProperty> security = new HashMap<String, SecurityProperty>() {
            {
                put("petstore_auth", securityPropertyPetAUth);
                put("random", securityPropertyRandom);
            }
        };

        securityIgnore.setSecurity(security);

        assertEquals(securityIgnore, securityIgnoreFromFile);

    }

    @Test
    public void testContentDeserialization() throws FileNotFoundException {
        Map<String, Object> result = loadMap(".content");

        Content contentIgnore = new Content();
        Content securityIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(result, Content.class);

        ContentSchema contentSchemaJson = new ContentSchema();
        ContentProperties contentPropertiesJson = new ContentProperties();
        contentPropertiesJson.setProperties(Collections.singletonList("petId"));
        contentSchemaJson.setSchema(contentPropertiesJson);

        ContentSchema contentSchemaXml = new ContentSchema();
        ContentProperties contentPropertiesXml = new ContentProperties();
        contentPropertiesXml.setProperties(Collections.singletonList("orderId"));
        contentSchemaXml.setSchema(contentPropertiesXml);

        Map<String, ContentSchema> response = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaJson);
                put("application/xml", contentSchemaXml);
            }
        };

        contentIgnore.setContent(response);

        assertEquals(contentIgnore, securityIgnoreFromFile);

    }

    @Test
    public void testRequestDeserialization() throws FileNotFoundException {
        Map<String, Object> result = loadMap(".request");

        RequestIgnore requestIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(result, RequestIgnore.class);
        RequestIgnore requestIgnore = new RequestIgnore();

        Content contentRequest = new Content();

        ContentSchema contentSchemaRequestJson = new ContentSchema();
        ContentProperties contentPropertiesRequestJson = new ContentProperties();
        contentPropertiesRequestJson.setProperties(Collections.singletonList("petId"));
        contentSchemaRequestJson.setSchema(contentPropertiesRequestJson);

        ContentSchema contentSchemaRequestUrlEncoded = new ContentSchema();
        ContentProperties contentPropertiesRequestUrlEncoded = new ContentProperties();
        contentPropertiesRequestUrlEncoded.setProperties(Collections.singletonList("orderId"));
        contentSchemaRequestUrlEncoded.setSchema(contentPropertiesRequestUrlEncoded);

        Map<String, ContentSchema> request = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaRequestJson);
                put("application/x-www-form-urlencoded", contentSchemaRequestUrlEncoded);
            }
        };

        contentRequest.setContent(request);
        requestIgnore.setRequest(contentRequest);


        assertEquals(requestIgnore, requestIgnoreFromFile);

    }

    @Test
    public void testResponseDeserialization() throws FileNotFoundException {
        Map<String, Object> result = loadMap(".response");

        ResponseIgnore responseIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(result, ResponseIgnore.class);
        ResponseIgnore responseIgnore = new ResponseIgnore();

        Content content = new Content();
        StatusIgnore statusIgnore = new StatusIgnore();

        ContentSchema contentSchemaJson = new ContentSchema();
        ContentProperties contentPropertiesJson = new ContentProperties();
        contentPropertiesJson.setProperties(Collections.singletonList("petId"));
        contentSchemaJson.setSchema(contentPropertiesJson);

        ContentSchema contentSchemaXml = new ContentSchema();
        ContentProperties contentPropertiesXml = new ContentProperties();
        contentPropertiesXml.setProperties(Collections.singletonList("orderId"));
        contentSchemaXml.setSchema(contentPropertiesXml);

        Map<String, ContentSchema> response = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaJson);
                put("application/xml", contentSchemaXml);
            }
        };

        content.setContent(response);

        Map<String, Content> status = new HashMap<String, Content>() {
            {
                put("200", content);
            }
        };

        statusIgnore.setStatus(status);
        responseIgnore.setResponse(statusIgnore);


        assertEquals(responseIgnore, responseIgnoreFromFile);

    }

    @Test
    public void testOperationDeserialization() throws FileNotFoundException {
        Map<String, Object> result = loadMap(".http_method");

        HttpMethodIgnore HttpMethodIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(result, HttpMethodIgnore.class);
        HttpMethodIgnore httpMethodIgnore = new HttpMethodIgnore();

        OperationIgnore operationIgnorePost = new OperationIgnore();

        ResponseIgnore responseIgnore = new ResponseIgnore();
        RequestIgnore requestIgnore = new RequestIgnore();
        SecurityIgnore securityIgnore = new SecurityIgnore();
        ParametersIgnore parametersIgnore = new ParametersIgnore();

        StatusIgnore statusIgnore = new StatusIgnore();

        SecurityProperty securityPropertyPetAUth = new SecurityProperty();
        SecurityProperty securityPropertyRandom = new SecurityProperty();

        securityPropertyRandom.setProperties(Collections.singletonList("write:random"));
        securityPropertyPetAUth.setProperties(Arrays.asList("write:pets", "write:pests", "read:pets"));

        Map<String, SecurityProperty> security = new HashMap<String, SecurityProperty>() {
            {
                put("petstore_auth", securityPropertyPetAUth);
                put("random", securityPropertyRandom);
            }
        };
        securityIgnore.setSecurity(security);

        Content content = new Content();

        ContentSchema contentSchemaJson = new ContentSchema();
        ContentProperties contentPropertiesJson = new ContentProperties();
        contentPropertiesJson.setProperties(Collections.singletonList("petId"));
        contentSchemaJson.setSchema(contentPropertiesJson);

        ContentSchema contentSchemaXml = new ContentSchema();
        ContentProperties contentPropertiesXml = new ContentProperties();
        contentPropertiesXml.setProperties(Collections.singletonList("orderId"));
        contentSchemaXml.setSchema(contentPropertiesXml);

        Map<String, ContentSchema> response = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaJson);
                put("application/xml", contentSchemaXml);
            }
        };

        content.setContent(response);

        Map<String, Content> status = new HashMap<String, Content>() {
            {
                put("200", content);
            }
        };

        statusIgnore.setStatus(status);
        responseIgnore.setResponse(statusIgnore);

        Content contentRequest = new Content();

        ContentSchema contentSchemaRequestJson = new ContentSchema();
        ContentProperties contentPropertiesRequestJson = new ContentProperties();
        contentPropertiesRequestJson.setProperties(Collections.singletonList("petId"));
        contentSchemaRequestJson.setSchema(contentPropertiesRequestJson);

        ContentSchema contentSchemaRequestUrlEncoded = new ContentSchema();
        ContentProperties contentPropertiesRequestUrlEncoded = new ContentProperties();
        contentPropertiesRequestUrlEncoded.setProperties(Collections.singletonList("orderId"));
        contentSchemaRequestUrlEncoded.setSchema(contentPropertiesRequestUrlEncoded);

        Map<String, ContentSchema> request = new HashMap<String, ContentSchema>() {
            {
                put("application/json", contentSchemaRequestJson);
                put("application/x-www-form-urlencoded", contentSchemaRequestUrlEncoded);
            }
        };

        contentRequest.setContent(request);
        requestIgnore.setRequest(contentRequest);

        parametersIgnore.setParameters(Arrays.asList("username", "password"));


        operationIgnorePost.setParameters(parametersIgnore);
        operationIgnorePost.setSecurity(securityIgnore);
        operationIgnorePost.setRequest(requestIgnore);
        operationIgnorePost.setResponse(responseIgnore);

        httpMethodIgnore.setPost(operationIgnorePost);

        assertEquals(httpMethodIgnore, HttpMethodIgnoreFromFile);

    }

    private Map<String, Object> loadMap(String path) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource(path).getFile()));

        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }
}