package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.ObjectMapperFactory;
import com.github.elibracha.models.ignore.SecurityIgnore;
import com.github.elibracha.models.ignore.SecurityProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SecurityDeserializer extends AbstractDeserializer<SecurityIgnore> {

    public SecurityDeserializer() {
        super(SecurityIgnore.class);
    }

    @Override
    public SecurityIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode securities = jsonParser.getCodec().readTree(jsonParser);
        SecurityIgnore securityIgnore = (SecurityIgnore) preProcess(new SecurityIgnore(), securities);

        Map<String, SecurityProperty> requirements = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = securities.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> securityScope = it.next();

            SecurityProperty propertyIgnore = ObjectMapperFactory.createYaml().convertValue(securityScope.getValue(), SecurityProperty.class);
            requirements.put(securityScope.getKey(), propertyIgnore);
        }

        securityIgnore.setSecurity(requirements);
        return securityIgnore;
    }
}