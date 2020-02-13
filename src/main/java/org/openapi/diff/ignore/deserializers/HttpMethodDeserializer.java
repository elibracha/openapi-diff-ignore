package org.openapi.diff.ignore.deserializers;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;
import org.openapi.diff.ignore.models.validations.enums.HttpMethodSupport;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpMethodDeserializer extends AbstractDeserializer<HttpMethodIgnore> {

    public HttpMethodDeserializer() {
        super(HttpMethodIgnore.class);
    }

    @Override
    public HttpMethodIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode httpMethods = jsonParser.getCodec().readTree(jsonParser);
        HttpMethodIgnore httpMethodIgnore = (HttpMethodIgnore) preProcess(new HttpMethodIgnore(), httpMethods);

        for (Iterator<Map.Entry<String, JsonNode>> it = httpMethods.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> HttpMethodScope = it.next();

            OperationIgnore operationIgnore = ObjectMapperFactory.createYaml().convertValue(HttpMethodScope.getValue(), OperationIgnore.class);

            if (checkWildCards(HttpMethodScope.getKey())) {
                List<String> keys = extractWildCards(HttpMethodScope.getKey());
                if (keys == null) {
                    List<String> supported = Arrays.stream(HttpMethodSupport.values())
                            .map(HttpMethodSupport::getValue)
                            .collect(Collectors.toList());

                    for (String sup : supported)
                        setMethod(sup, httpMethodIgnore, operationIgnore);
                } else {
                    for (String sup : keys)
                        setMethod(sup, httpMethodIgnore, operationIgnore);
                }
            } else {
                setMethod(HttpMethodScope.getKey(), httpMethodIgnore, operationIgnore);
            }
        }

        return httpMethodIgnore;
    }

    private void setMethod(String key, HttpMethodIgnore httpMethodIgnore,
                           OperationIgnore operationIgnore) throws SpecificationSupportException {
        switch (key) {
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
                        key));
        }
    }
}
