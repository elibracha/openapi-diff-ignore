package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.*;
import org.openapi.diff.ignore.models.validations.enums.OperationSupport;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationDeserializer extends StdDeserializer<OperationIgnore> {

    public OperationDeserializer() {
        super(OperationIgnore.class);
    }

    public static void methodPostProcess(OperationIgnore operationIgnore) {
        int operationCounter = 0;
        int operationIgnoredCounter = 0;

        List<String> supported = Arrays.stream(OperationSupport.values())
                .map(OperationSupport::getValue)
                .collect(Collectors.toList());

        for (String type : supported) {
            IgnoreElemnt ignoreElemnt = operationIgnore.checkIfIgnoreExist(type);
            operationCounter += ignoreElemnt != null ? 1 : 0;
            operationIgnoredCounter += ignoreElemnt != null && ignoreElemnt.isIgnoreAll() ? 1 : 0;
        }

        if (operationCounter == operationIgnoredCounter)
            operationIgnore.setIgnoreAll(true);
    }

    @Override
    public OperationIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode operation = jsonParser.getCodec().readTree(jsonParser);
        OperationIgnore operationIgnore = new OperationIgnore();

        if (!operation.isContainerNode()) {
            operationIgnore.setIgnoreAll(true);
            return operationIgnore;
        }

        for (Iterator<Map.Entry<String, JsonNode>> it = operation.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> operationScope = it.next();

            switch (operationScope.getKey()) {
                case SpecConstants.OperationIgnoresEntries.PARAMETERS:
                    ParametersIgnore parametersIgnore = ObjectMapperFactory.createYaml().convertValue(operationScope, ParametersIgnore.class);
                    operationIgnore.setParameters(parametersIgnore);
                    break;
                case SpecConstants.OperationIgnoresEntries.REQUEST:
                    RequestIgnore requestIgnore = ObjectMapperFactory.createYaml().convertValue(operationScope, RequestIgnore.class);
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

        methodPostProcess(operationIgnore);

        return operationIgnore;
    }
}
