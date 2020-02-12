package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.ContentProperties;

import java.io.IOException;

public class ContentPropertiesDeserializer extends StdDeserializer<ContentProperties> {

    public ContentPropertiesDeserializer() {
        super(ContentProperties.class);
    }

    @Override
    public ContentProperties deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
