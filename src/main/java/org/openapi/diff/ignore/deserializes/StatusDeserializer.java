package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.StatusIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatusDeserializer extends StdDeserializer<StatusIgnore> {

    public StatusDeserializer() {
        super(StatusIgnore.class);
    }

    @Override
    public StatusIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode statuses = jsonParser.getCodec().readTree(jsonParser);
        StatusIgnore statusIgnore = new StatusIgnore();

        if (!statuses.isContainerNode()) {
            statusIgnore.setIgnoreAll(true);
            return statusIgnore;
        }

        Map<String, Content> st = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = statuses.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> statusScope = it.next();

            for (Iterator<Map.Entry<String, JsonNode>> contentIt = statusScope.getValue().fields(); contentIt.hasNext(); ) {
                Map.Entry<String, JsonNode> contentScope = contentIt.next();
                Content contentIgnore = ObjectMapperFactory.createYaml().convertValue(contentScope.getValue(), Content.class);
                st.put(statusScope.getKey(), contentIgnore);
            }

        }

        statusIgnore.setStatus(st);
        return statusIgnore;
    }
}
