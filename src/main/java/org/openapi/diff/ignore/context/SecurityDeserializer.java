package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.SecurityIgnore;
import org.openapi.diff.ignore.models.ignore.SecurityProperty;

import java.io.IOException;
import java.util.*;

public class SecurityDeserializer extends StdDeserializer<SecurityIgnore> {

    public SecurityDeserializer() {
        super(SecurityIgnore.class);
    }

    @Override
    public SecurityIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode securities = jsonParser.getCodec().readTree(jsonParser);
        SecurityIgnore securityIgnore = new SecurityIgnore();

        Map<String, SecurityProperty> requirements = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = securities.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> securityScope = it.next();

            SecurityProperty securityProperty = new SecurityProperty();
            List<String> properties = new ArrayList<>();

            for (JsonNode prop : securityScope.getValue()) {
                properties.add(prop.asText());
            }

            securityProperty.setProperties(properties);
            requirements.put(securityScope.getKey(), securityProperty);
        }

        securityIgnore.setSecurity(requirements);
        return securityIgnore;
    }
}