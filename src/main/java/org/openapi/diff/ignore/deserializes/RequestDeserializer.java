package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.RequestIgnore;

import java.io.IOException;

public class RequestDeserializer extends StdDeserializer<RequestIgnore> {

    public RequestDeserializer() {
        super(RequestIgnore.class);
    }

    @Override
    public RequestIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode content = jsonParser.getCodec().readTree(jsonParser);
        RequestIgnore requestIgnore = new RequestIgnore();

        if (!content.isContainerNode()) {
            requestIgnore.setIgnoreAll(true);
            return requestIgnore;
        }

        Content contentIgnore = ObjectMapperFactory.createYaml().convertValue(content, Content.class);

        requestIgnore.setRequest(contentIgnore);

        return requestIgnore;
    }
}
