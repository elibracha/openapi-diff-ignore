package org.openapi.diff.ignore.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.ResponseIgnore;
import org.openapi.diff.ignore.models.ignore.StatusIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResponseDeserializer extends AbstractDeserializer<ResponseIgnore> {

    public ResponseDeserializer() {
        super(ResponseIgnore.class);
    }

    @Override
    public ResponseIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode response = jsonParser.getCodec().readTree(jsonParser);
        ResponseIgnore responseIgnore = (ResponseIgnore) preProcess(new ResponseIgnore(), response);

        Map<String, StatusIgnore> responseContent = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = response.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> responseScope = it.next();

            StatusIgnore statusIgnore = ObjectMapperFactory.createYaml().convertValue(responseScope, StatusIgnore.class);
            responseContent.put(responseScope.getKey(), statusIgnore);

        }

        responseIgnore.setResponse(responseContent);
        return responseIgnore;
    }
}
