package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;
import org.openapi.diff.ignore.models.ignore.PathsIgnore;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ContextDeserializer extends StdDeserializer<ContextIgnore> {

    public ContextDeserializer() {
        super(ContextIgnore.class);
    }

    @Override
    public ContextIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        ContextIgnore contextIgnore = new ContextIgnore();

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> globalScope = it.next();

            switch (globalScope.getKey()) {
                case SpecConstants.ContextEntries.VERSION:
                    contextIgnore.setVersion(globalScope.getValue().asText());
                    break;
                case SpecConstants.ContextEntries.PROJECT:
                    contextIgnore.setProject(globalScope.getValue().asText());
                    break;
                case SpecConstants.ContextEntries.INFO:
                    contextIgnore.setInfo(globalScope.getValue().asText());
                    break;
                case SpecConstants.ContextEntries.PATHS:
                    contextIgnore.setPaths(objectMapper.convertValue(globalScope.getValue(), PathsIgnore.class));
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\", please referenced the documentation for supported entries.",
                            globalScope.getKey()));
            }
        }

        return contextIgnore;
    }
}
