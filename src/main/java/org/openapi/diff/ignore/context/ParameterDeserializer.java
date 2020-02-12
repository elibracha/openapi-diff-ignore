package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.ParametersIgnore;

import java.io.IOException;

public class ParameterDeserializer extends StdDeserializer<ParametersIgnore> {

    public ParameterDeserializer() {
        super(ParametersIgnore.class);
    }

    @Override
    public ParametersIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return null;
    }
}
