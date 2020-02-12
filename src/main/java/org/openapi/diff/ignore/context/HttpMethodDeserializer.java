package org.openapi.diff.ignore.context;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class HttpMethodDeserializer extends StdDeserializer<HttpMethodIgnore> {

    public HttpMethodDeserializer() {
        super(HttpMethodIgnore.class);
    }

    @Override
    public HttpMethodIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode httpMethods = jsonParser.getCodec().readTree(jsonParser);
        HttpMethodIgnore httpMethodIgnore = new HttpMethodIgnore();

        for (Iterator<Map.Entry<String, JsonNode>> it = httpMethods.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> operationScope = it.next();

            OperationIgnore operationIgnore = ObjectMapperFactory.createYaml().convertValue(it.next().getValue(), OperationIgnore.class);

            switch (operationScope.getKey()) {
                case "post":
                    httpMethodIgnore.setPost(operationIgnore);
                    break;
                case "get":
                    httpMethodIgnore.setGet(operationIgnore);
                    break;
                case "put":
                    httpMethodIgnore.setPut(operationIgnore);
                    break;
                case "delete":
                    httpMethodIgnore.setDelete(operationIgnore);
                    break;
                case "connect":
                    httpMethodIgnore.setConnect(operationIgnore);
                    break;
                case "head":
                    httpMethodIgnore.setHead(operationIgnore);
                    break;
                case "options":
                    httpMethodIgnore.setOptions(operationIgnore);
                    break;
                case "trace":
                    httpMethodIgnore.setTrace(operationIgnore);
                    break;
                case "patch":
                    httpMethodIgnore.setPatch(operationIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as an http method, please referenced the documentation for supported entries.",
                            operationScope.getKey()));
            }
        }

        return httpMethodIgnore;
    }
}
