package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.models.ignore.SecurityProperty;

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
