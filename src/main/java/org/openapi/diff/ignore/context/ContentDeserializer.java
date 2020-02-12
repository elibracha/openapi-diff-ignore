package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.Content;

import java.io.IOException;

public class ContentDeserializer extends StdDeserializer<Content> {

    public ContentDeserializer() {
        super(Content.class);
    }

    @Override
    public Content deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
