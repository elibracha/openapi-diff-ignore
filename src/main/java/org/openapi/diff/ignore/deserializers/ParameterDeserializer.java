package org.openapi.diff.ignore.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.models.ignore.ParametersIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParameterDeserializer extends AbstractDeserializer<ParametersIgnore> {

    public ParameterDeserializer() {
        super(ParametersIgnore.class);
    }

    @Override
    public ParametersIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode parameters = jsonParser.getCodec().readTree(jsonParser);
        ParametersIgnore parametersIgnore = (ParametersIgnore) preProcess(new ParametersIgnore(), parameters);

        List<String> params = new ArrayList<>();
        for (JsonNode param : parameters) {
            params.add(param.asText());
        }

        parametersIgnore.setParameters(params);

        return parametersIgnore;
    }
}
