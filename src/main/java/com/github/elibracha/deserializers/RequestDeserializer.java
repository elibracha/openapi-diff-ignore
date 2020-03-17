package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.ObjectMapperFactory;
import com.github.elibracha.models.SpecConstants;
import com.github.elibracha.models.ignore.Content;
import com.github.elibracha.models.ignore.RequestIgnore;
import com.github.elibracha.exceptions.SpecificationSupportException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RequestDeserializer extends AbstractDeserializer<RequestIgnore> {

    public RequestDeserializer() {
        super(RequestIgnore.class);
    }

    @Override
    public RequestIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode requestScope = jsonParser.getCodec().readTree(jsonParser);
        RequestIgnore requestIgnore = (RequestIgnore) preProcess(new RequestIgnore(), requestScope);

        for (Iterator<Map.Entry<String, JsonNode>> it = requestScope.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> requestContentScope = it.next();

            switch (requestContentScope.getKey()) {
                case SpecConstants.RequestEntries.CONTENT:
                    Content contentIgnore = ObjectMapperFactory.createYaml().convertValue(requestContentScope.getValue(), Content.class);
                    requestIgnore.setContent(contentIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as an request ignore type, please referenced the documentation for supported entries.",
                            requestContentScope.getKey()));
            }
        }

        return requestIgnore;
    }
}
