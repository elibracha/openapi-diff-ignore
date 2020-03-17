package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.ObjectMapperFactory;
import com.github.elibracha.models.ignore.Content;
import com.github.elibracha.models.ignore.ContentSchema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContentDeserializer extends AbstractDeserializer<Content> {

    public ContentDeserializer() {
        super(Content.class);
    }

    @Override
    public Content deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode contentScope = jsonParser.getCodec().readTree(jsonParser);
        Content content = (Content) preProcess(new Content(), contentScope);

        Map<String, ContentSchema> contentSchemaMap = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = contentScope.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> contentSchemaScope = it.next();
            ContentSchema contentSchemaIgnore = ObjectMapperFactory.createYaml().convertValue(contentSchemaScope.getValue(), ContentSchema.class);
            if (checkWildCards(contentSchemaScope.getKey())) {
                for (String sup : extractWildCards(contentSchemaScope.getKey()))
                    contentSchemaMap.put(sup, contentSchemaIgnore);
            } else {
                contentSchemaMap.put(contentSchemaScope.getKey(), contentSchemaIgnore);
            }
        }

        content.setContentSchemas(contentSchemaMap);

        return content;
    }
}
