package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.RequestIgnore;

import java.io.IOException;

public class RequestDeserializer extends StdDeserializer<RequestIgnore> {

    public RequestDeserializer() {
        super(RequestIgnore.class);
    }

    @Override
    public RequestIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
