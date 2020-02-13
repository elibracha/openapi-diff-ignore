package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.SecurityIgnore;
import org.openapi.diff.ignore.models.ignore.SecurityProperty;

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