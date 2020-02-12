package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.ParametersIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParameterDeserializer extends StdDeserializer<ParametersIgnore> {

    public ParameterDeserializer() {
        super(ParametersIgnore.class);
    }

    @Override
    public ParametersIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode parameters = jsonParser.getCodec().readTree(jsonParser);
        ParametersIgnore parametersIgnore = new ParametersIgnore();

        if (!parameters.isContainerNode()) {
            parametersIgnore.setIgnoreAll(true);
            return parametersIgnore;
        }

        List<String> params = new ArrayList<>();
        for (JsonNode param : parameters) {
            params.add(param.asText());
        }

        parametersIgnore.setParameters(params);

        return parametersIgnore;
    }
}
