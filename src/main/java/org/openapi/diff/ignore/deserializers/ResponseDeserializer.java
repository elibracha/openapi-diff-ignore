package org.openapi.diff.ignore.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.ResponseIgnore;
import org.openapi.diff.ignore.models.ignore.StatusIgnore;

import java.io.IOException;

public class ResponseDeserializer extends AbstractDeserializer<ResponseIgnore> {

    public ResponseDeserializer() {
        super(ResponseIgnore.class);
    }

    @Override
    public ResponseIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode response = jsonParser.getCodec().readTree(jsonParser);
        ResponseIgnore responseIgnore = (ResponseIgnore) preProcess(new ResponseIgnore(), response);

        StatusIgnore statusIgnore = ObjectMapperFactory.createYaml().convertValue(response, StatusIgnore.class);

        responseIgnore.setResponse(statusIgnore);
        return responseIgnore;
    }
}
