package org.openapi.diff.ignore.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.StatusIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatusDeserializer extends AbstractDeserializer<StatusIgnore> {

    public StatusDeserializer() {
        super(StatusIgnore.class);
    }

    @Override
    public StatusIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode statuses = jsonParser.getCodec().readTree(jsonParser);
        StatusIgnore statusIgnore = new StatusIgnore();

        Map<String, Content> st = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = statuses.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> statusScope = it.next();

            statusIgnore = (StatusIgnore) preProcess(statusIgnore, statusScope.getValue());

            if (checkWildCards(statusScope.getKey())) {
                List<String> keys = extractWildCards(statusScope.getKey());

                for (String sup : keys)
                    setMethod(sup, statusScope, st);
            } else {
                setMethod(statusScope.getKey(), statusScope, st);
            }
        }

        statusIgnore.setStatus(st);
        return statusIgnore;
    }

    private void setMethod(String key, Map.Entry<String, JsonNode> statusScope, Map<String, Content> st) {
        Content content = new Content();

        for (Iterator<Map.Entry<String, JsonNode>> contentIt = statusScope.getValue().fields(); contentIt.hasNext(); ) {
            Map.Entry<String, JsonNode> contentScope = contentIt.next();
            switch (contentScope.getKey()) {
                case "new":
                    content.setNewContent(contentScope.getValue().booleanValue());
                case "content":
                    Content contentIgnore = ObjectMapperFactory.createYaml().convertValue(contentScope.getValue(), Content.class);
                    content.setContentSchemas(contentIgnore.getContentSchemas());
                    content.setIgnoreAll(contentIgnore.isIgnoreAll());
            }
        }

        st.put(key, content);
    }
}
