package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;

import java.io.IOException;

public class ResponseDeserializer extends StdDeserializer<ContextIgnore> {

    protected ResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ContextIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
