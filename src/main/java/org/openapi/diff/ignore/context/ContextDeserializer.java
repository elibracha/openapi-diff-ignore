package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.exceptions.InvalidVersionException;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.ignore.GlobalIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;
import org.openapi.diff.ignore.models.ignore.PathIgnore;
import org.openapi.diff.ignore.models.ignore.SecurityIgnore;

import java.io.IOException;
import java.util.*;

public class ContextDeserializer extends StdDeserializer<GlobalIgnore> {

    private static final List<String> versions = Arrays.asList(
            "1.0.0",
            "1.0.1"
    );

    private GlobalIgnore globalIgnore;

    protected ContextDeserializer(Class<?> vc) {
        super(vc);
        this.globalIgnore = new GlobalIgnore();
    }

    protected ContextDeserializer(JavaType valueType) {
        super(valueType);
        this.globalIgnore = new GlobalIgnore();
    }

    protected ContextDeserializer(StdDeserializer<?> src) {
        super(src);
        this.globalIgnore = new GlobalIgnore();
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
                            OperationIgnore operationIgnore = new OperationIgnore();

                            for (Iterator<Map.Entry<String, JsonNode>> operationScope = pathIt.getValue().fields(); operationScope.hasNext(); ) {
                                Map.Entry<String, JsonNode> ignoresIt = operationScope.next();
                                PathIgnore pathIgnore = new PathIgnore();

                                switch (ignoresIt.getKey()) {
                                    case "post":
                                        postParsing(ignoresIt, operationIgnore, pathIt, pathIgnore);
                                        break;
                                    case "put":
                                    case "delete":
                                    case "get":
                                    case "trace":
                                    case "head":
                                    case "options":
                                    case "patch":
                                    default:
                                        throw new SpecificationSupportException(String.format(
                                                "Specification does not support value \"%s\" please referenced the documentation for supported entries.",
                                                ignoresIt.getKey()));
                                }
                            }
                        }
                    }
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" please referenced the documentation for supported entries.",
                            globalScope.getKey()));


            }

        }
        return globalIgnore;
    }


    private void postParsing(Map.Entry<String, JsonNode> ignoresIt, OperationIgnore operationIgnore,
                             Map.Entry<String, JsonNode> pathIt, PathIgnore pathIgnore) throws SpecificationSupportException {

        for (Iterator<Map.Entry<String, JsonNode>> ignoresScope = ignoresIt.getValue().fields(); ignoresScope.hasNext(); ) {
            Map.Entry<String, JsonNode> ignoreType = ignoresScope.next();

            switch (ignoreType.getKey()) {
                case "response":
                case "request":
                case "security":
                    securityParsing(ignoreType, pathIgnore);
                    break;
                case "parameters":
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as ignore type please referenced the documentation for supported entries.",
                            ignoreType.getKey()));
            }
            operationIgnore.setPost(pathIgnore);
            this.globalIgnore.getPaths().put(pathIt.getKey(), (operationIgnore));
        }
    }


    private void securityParsing(Map.Entry<String, JsonNode> ignoreType, PathIgnore pathIgnore) {
        SecurityIgnore securityIgnore = new SecurityIgnore();

        if (ignoreType.getValue().elements().hasNext()) {
            for (Iterator<JsonNode> it = ignoreType.getValue().elements(); it.hasNext(); ) {
                JsonNode securityNode = it.next();
                Map<String, List<String>> securityMap = new HashMap<>();

                for (Iterator<Map.Entry<String, JsonNode>> securityScope = securityNode.fields(); securityScope.hasNext(); ) {
                    Map.Entry<String, JsonNode> securityReq = securityScope.next();

                    List<String> securityReqList = new ArrayList<>();

                    for (Iterator<JsonNode> securityReqIt = securityReq.getValue().elements(); securityReqIt.hasNext(); ) {
                        JsonNode requirement = securityReqIt.next();
                        securityReqList.add(requirement.textValue());
                    }
                    securityMap.put(securityReq.getKey(), securityReqList);
                }

                securityIgnore.setSecurities(securityMap);
            }

        }
        pathIgnore.setSecurityIgnore(securityIgnore);
    }
}
