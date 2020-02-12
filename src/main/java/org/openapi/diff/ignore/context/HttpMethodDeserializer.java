package org.openapi.diff.ignore.context;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.SpecConstants;
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

        if (!httpMethods.isContainerNode()) {
            httpMethodIgnore.setIgnoreAll(true);
            return httpMethodIgnore;
        }

        for (Iterator<Map.Entry<String, JsonNode>> it = httpMethods.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> HttpMethodScope = it.next();

            OperationIgnore operationIgnore = ObjectMapperFactory.createYaml().convertValue(HttpMethodScope.getValue(), OperationIgnore.class);

            switch (HttpMethodScope.getKey()) {
                case SpecConstants.HttpMethods.POST:
                    httpMethodIgnore.setPost(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.GET:
                    httpMethodIgnore.setGet(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.PUT:
                    httpMethodIgnore.setPut(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.DELETE:
                    httpMethodIgnore.setDelete(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.HEAD:
                    httpMethodIgnore.setHead(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.OPTIONS:
                    httpMethodIgnore.setOptions(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.TRACE:
                    httpMethodIgnore.setTrace(operationIgnore);
                    break;
                case SpecConstants.HttpMethods.PATCH:
                    httpMethodIgnore.setPatch(operationIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as an http method, please referenced the documentation for supported entries.",
                            HttpMethodScope.getKey()));
            }
        }

        return httpMethodIgnore;
    }
}
