package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.exceptions.InvalidVersionException;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.ignore.GlobalIgnore;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContextDeserializer extends StdDeserializer<GlobalIgnore> {

    private static final List<String> versions = Arrays.asList(
            "1.0.0"
    );

    protected ContextDeserializer(Class<?> vc) {
        super(vc);
    }

    protected ContextDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ContextDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public GlobalIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        GlobalIgnore globalIgnore = new GlobalIgnore();

        for (Iterator<Map.Entry<String, JsonNode>> globalIt = node.fields(); globalIt.hasNext(); ) {
            Map.Entry<String, JsonNode> globalScope = globalIt.next();

            switch (globalScope.getKey()) {
                case "version":
                    if (versions.contains(globalScope.getValue().asText())) {
                        globalIgnore.setVersion(globalScope.getValue().asText());
                    } else {
                        throw new InvalidVersionException(String.format(
                                "Version \"%s\" that was specified is invalid.",
                                globalScope.getValue().asText()));
                    }
                    break;
                case "project":
                    globalIgnore.setProject(globalScope.getValue().asText());
                    break;
                case "info":
                    globalIgnore.setInfo(globalScope.getValue().asText());
                case "paths":
                    if (globalScope.getValue().isContainerNode()) {
                        for (Iterator<Map.Entry<String, JsonNode>> pathsScope = globalScope.getValue().fields(); pathsScope.hasNext(); ) {

                            Map.Entry<String, JsonNode> pathIt = pathsScope.next();

                            for (Iterator<Map.Entry<String, JsonNode>> operationScope = pathIt.getValue().fields(); operationScope.hasNext(); ) {
                                Map.Entry<String, JsonNode> ignoresIt = operationScope.next();

                                for (Iterator<Map.Entry<String, JsonNode>> ignoresScope = ignoresIt.getValue().fields(); ignoresScope.hasNext(); ) {
                                    switch (ignoresScope.next().getKey()) {
                                        case "response":
                                        case "request":
                                        case "security":
                                        case "parameters":
                                        default:
                                            throw new SpecificationSupportException(String.format(
                                                    "Specification does not support value \"%s\" as ignore type please referenced the documentation for supported entries.",
                                                    globalScope.getValue().asText()));
                                    }

                                }
                            }
                        }
                    }
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" please referenced the documentation for supported entries.",
                            globalScope.getValue().asText()));


            }

        }

        return globalIgnore;
    }
}