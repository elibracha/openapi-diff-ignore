package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.elibracha.ObjectMapperFactory;
import com.github.elibracha.models.SpecConstants;
import com.github.elibracha.models.ignore.*;
import com.github.elibracha.exceptions.SpecificationSupportException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class OperationDeserializer extends AbstractDeserializer<OperationIgnore> {

    public OperationDeserializer() {
        super(OperationIgnore.class);
    }

    @Override
    public OperationIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode operation = jsonParser.getCodec().readTree(jsonParser);
        OperationIgnore operationIgnore = (OperationIgnore) preProcess(new OperationIgnore(), operation);

        for (Iterator<Map.Entry<String, JsonNode>> it = operation.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> operationScope = it.next();

            switch (operationScope.getKey()) {
                case SpecConstants.OperationIgnoresEntries.PARAMETERS:
                    ParametersIgnore parametersIgnore = ObjectMapperFactory.createYaml().convertValue(operationScope.getValue(), ParametersIgnore.class);
                    operationIgnore.setParameters(parametersIgnore);
                    break;
                case SpecConstants.OperationIgnoresEntries.REQUEST:
                    RequestIgnore requestIgnore = ObjectMapperFactory.createYaml().convertValue(operationScope.getValue(), RequestIgnore.class);
                    operationIgnore.setRequest(requestIgnore);
                    break;
                case SpecConstants.OperationIgnoresEntries.RESPONSE:
                    ResponseIgnore responseIgnore = ObjectMapperFactory.createYaml().convertValue(operationScope.getValue(), ResponseIgnore.class);
                    operationIgnore.setResponse(responseIgnore);
                    break;
                case SpecConstants.OperationIgnoresEntries.SECURITY:
                    SecurityIgnore securityIgnore = ObjectMapperFactory.createYaml().convertValue(operationScope.getValue(), SecurityIgnore.class);
                    operationIgnore.setSecurity(securityIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as an operation to ignore, please referenced the documentation for supported entries.",
                            operationScope.getKey()));
            }
        }

        return operationIgnore;
    }
}
