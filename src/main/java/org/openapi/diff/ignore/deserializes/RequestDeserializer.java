package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.RequestIgnore;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RequestDeserializer extends StdDeserializer<RequestIgnore> {

    public RequestDeserializer() {
        super(RequestIgnore.class);
    }

    @Override
    public RequestIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode requestScope = jsonParser.getCodec().readTree(jsonParser);
        RequestIgnore requestIgnore = new RequestIgnore();

        if (!requestScope.isContainerNode()) {
            requestIgnore.setIgnoreAll(true);
            return requestIgnore;
        }

        for (Iterator<Map.Entry<String, JsonNode>> it = requestScope.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> requestContentScope = it.next();

            switch (requestContentScope.getKey()) {
                case "content":
                    Content contentIgnore = ObjectMapperFactory.createYaml().convertValue(requestContentScope.getValue(), Content.class);
                    requestIgnore.setRequest(contentIgnore);
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
