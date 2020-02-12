package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.SecurityProperty;

import java.io.IOException;

public class SecurityPropertyDeserializer extends StdDeserializer<SecurityProperty> {

    public SecurityPropertyDeserializer() {
        super(SecurityProperty.class);
    }

    @Override
    public SecurityProperty deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
