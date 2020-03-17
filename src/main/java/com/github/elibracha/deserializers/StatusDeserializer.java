package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.ObjectMapperFactory;
import com.github.elibracha.models.ignore.Content;
import com.github.elibracha.models.ignore.StatusIgnore;

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
                    if (statusIgnore.isIgnoreAll())
                        setMethod(sup, statusScope, st, true);
                    else {
                        setMethod(sup, statusScope, st, false);
                    }
            } else {
                if (statusIgnore.isIgnoreAll())
                    setMethod(statusScope.getKey(), statusScope, st, true);
                else {
                    setMethod(statusScope.getKey(), statusScope, st, false);
                }
            }
        }

        statusIgnore.setStatus(st);
        return statusIgnore;
    }

    private void setMethod(String key, Map.Entry<String, JsonNode> statusScope, Map<String, Content> st, boolean ignoreAll) {
        Content content = new Content();
        content.setIgnoreAll(ignoreAll);

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
