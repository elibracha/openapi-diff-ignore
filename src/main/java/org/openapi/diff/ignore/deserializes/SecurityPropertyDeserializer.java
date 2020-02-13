package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.models.ignore.SecurityProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityPropertyDeserializer extends AbstractDeserializer<SecurityProperty> {

    public SecurityPropertyDeserializer() {
        super(SecurityProperty.class);
    }

    @Override
    public SecurityProperty deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode properties = jsonParser.getCodec().readTree(jsonParser);
        SecurityProperty propertyIgnore = (SecurityProperty) preProcess(new SecurityProperty(), properties);

        List<String> props = new ArrayList<>();

        for (JsonNode prop : properties) {
            props.add(prop.asText());
        }

        propertyIgnore.setProperties(props);
        return propertyIgnore;
    }
}
