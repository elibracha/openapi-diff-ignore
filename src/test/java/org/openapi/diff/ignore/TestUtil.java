package org.openapi.diff.ignore;

import org.openapi.diff.ignore.models.ignore.*;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class TestUtil {

    public ContextIgnore getContext() {
        ContextIgnore contextIgnore = new ContextIgnore();

        contextIgnore.setPaths(getPaths());
        contextIgnore.setInfo("this is a sample ignore file to silence changes in openapi-diff");
        contextIgnore.setProject("sample-service");
        contextIgnore.setVersion("1.0.0");

        return contextIgnore;
    }

    public PathsIgnore getPaths() {
        PathsIgnore pathsIgnore = new PathsIgnore();

        HttpMethodIgnore httpMethodIgnore = new HttpMethodIgnore();
        httpMethodIgnore.setPut(getOperationPut());
        httpMethodIgnore.setGet(getOperationGet());
        httpMethodIgnore.setPost(getOperationPost());

        Map<String, HttpMethodIgnore> paths = new HashMap<String, HttpMethodIgnore>() {
            {
                put("/**", httpMethodIgnore);
            }
        };

        pathsIgnore.setPaths(paths);

        return pathsIgnore;
    }

    public HttpMethodIgnore getHttpMethodPost() {
        HttpMethodIgnore httpMethodIgnore = new HttpMethodIgnore();
        httpMethodIgnore.setPost(getOperationPost());
        return httpMethodIgnore;
    }

    public OperationIgnore getOperationPut() {
        OperationIgnore operationIgnore = new OperationIgnore();
        operationIgnore.setIgnoreAll(true);
        return operationIgnore;
    }

    public OperationIgnore getOperationGet() {
        OperationIgnore operationIgnore = new OperationIgnore();
        operationIgnore.setParameters(getParametersGet());
        return operationIgnore;
    }

    public OperationIgnore getOperationPost() {
        OperationIgnore operationIgnore = new OperationIgnore();

        operationIgnore.setParameters(getParametersPost());
        operationIgnore.setSecurity(getSecurityPost());
        operationIgnore.setRequest(getRequestPost());
        operationIgnore.setResponse(getResponsePost());

        return operationIgnore;
    }

    public ParametersIgnore getParametersGet() {
        ParametersIgnore parametersIgnore = new ParametersIgnore();
        parametersIgnore.setParameters(Arrays.asList("random", "test"));
        return parametersIgnore;
    }

    public ParametersIgnore getParametersPost() {
        ParametersIgnore parametersIgnore = new ParametersIgnore();
        parametersIgnore.setParameters(Arrays.asList("username", "password"));
        return parametersIgnore;
    }

    public SecurityIgnore getSecurityPost() {
        SecurityIgnore securityIgnore = new SecurityIgnore();

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

        return securityIgnore;
    }

    public Content getContentPost() {
        Content contentIgnore = new Content();

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

        contentIgnore.setContentSchemas(response);

        return contentIgnore;

    }

    public RequestIgnore getRequestPost() {
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

        contentRequest.setContentSchemas(request);
        requestIgnore.setContent(contentRequest);


        return requestIgnore;

    }

    public ResponseIgnore getResponsePost() {
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

        content.setContentSchemas(response);

        Map<String, Content> status = new HashMap<String, Content>() {
            {
                put("200", content);
            }
        };

        statusIgnore.setStatus(status);
        responseIgnore.setResponse(statusIgnore);


        return responseIgnore;
    }

    public Map<String, Object> loadMap(String path) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource(path).getFile()));

        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }
}
