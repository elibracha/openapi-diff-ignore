package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;

import java.io.IOException;

public class ContextDeserializer extends StdDeserializer<ContextIgnore> {

    private ContextIgnore contextIgnore;

    protected ContextDeserializer(Class<?> vc) {
        super(vc);
        this.contextIgnore = new ContextIgnore();
    }

    @Override
    public ContextIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {


        return null;
    }
}
