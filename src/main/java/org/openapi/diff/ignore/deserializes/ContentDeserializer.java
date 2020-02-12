package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.ContentSchema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContentDeserializer extends StdDeserializer<Content> {

    public ContentDeserializer() {
        super(Content.class);
    }

    @Override
    public Content deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode contentScope = jsonParser.getCodec().readTree(jsonParser);
        Content content = new Content();

        if (!contentScope.isContainerNode()) {
            content.setIgnoreAll(true);
            return content;
        }

        Map<String, ContentSchema> contentSchemaMap = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = contentScope.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> contentSchemaScope = it.next();
            ContentSchema contentSchemaIgnore = ObjectMapperFactory.createYaml().convertValue(contentSchemaScope.getValue(), ContentSchema.class);

            contentSchemaMap.put(contentSchemaScope.getKey(), contentSchemaIgnore);
        }

        content.setContent(contentSchemaMap);

        return content;
    }
}
