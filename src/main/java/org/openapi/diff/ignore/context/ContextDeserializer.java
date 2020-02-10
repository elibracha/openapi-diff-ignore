package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.exceptions.InvalidVersionException;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.ignore.*;

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

        for (Iterator<Map.Entry<String, JsonNode>> globalIt = node.fields(); globalIt.hasNext(); ) {
            Map.Entry<String, JsonNode> globalScope = globalIt.next();

            switch (globalScope.getKey()) {
                case "version":
                    validateVersion(globalScope);
                    break;
                case "project":
                    this.globalIgnore.setProject(globalScope.getValue().asText());
                    break;
                case "info":
                    this.globalIgnore.setInfo(globalScope.getValue().asText());
                    break;
                case "paths":
                    pathsParsing(globalScope);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" please referenced the documentation for supported entries.",
                            globalScope.getKey()));


            }

        }
        return this.globalIgnore;
    }


    private void pathsParsing(Map.Entry<String, JsonNode> globalScope) throws SpecificationSupportException {
        if (globalScope.getValue().isContainerNode()) {
            for (Iterator<Map.Entry<String, JsonNode>> pathsScope = globalScope.getValue().fields(); pathsScope.hasNext(); ) {

                Map.Entry<String, JsonNode> pathIt = pathsScope.next();
                OperationIgnore operationIgnore = new OperationIgnore();

                for (Iterator<Map.Entry<String, JsonNode>> operationScope = pathIt.getValue().fields(); operationScope.hasNext(); ) {
                    Map.Entry<String, JsonNode> ignoresIt = operationScope.next();
                    PathIgnore pathIgnore = new PathIgnore();

                    methodParsing(ignoresIt, operationIgnore, pathIt, pathIgnore);
                }
            }
        }
    }

    private void methodParsing(Map.Entry<String, JsonNode> ignoresIt, OperationIgnore operationIgnore,
                               Map.Entry<String, JsonNode> pathIt, PathIgnore pathIgnore) throws SpecificationSupportException {

        for (Iterator<Map.Entry<String, JsonNode>> ignoresScope = ignoresIt.getValue().fields(); ignoresScope.hasNext(); ) {
            Map.Entry<String, JsonNode> ignoreType = ignoresScope.next();

            switch (ignoreType.getKey()) {
                case "response":
                    responseParsing(ignoreType, pathIgnore);
                    break;
                case "request":
                    requestParsing(ignoreType, pathIgnore);
                    break;
                case "security":
                    securityParsing(ignoreType, pathIgnore);
                    break;
                case "parameters":
                    parameterParsing(ignoreType, pathIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as ignore type please referenced the documentation for supported entries.",
                            ignoreType.getKey()));
            }

            switch (ignoresIt.getKey()) {
                case "post":
                    operationIgnore.setPost(pathIgnore);
                    break;
                case "put":
                    operationIgnore.setPut(pathIgnore);
                    break;
                case "delete":
                    operationIgnore.setDelete(pathIgnore);
                    break;
                case "get":
                    operationIgnore.setGet(pathIgnore);
                    break;
                case "trace":
                    operationIgnore.setTrace(pathIgnore);
                    break;
                case "head":
                    operationIgnore.setHead(pathIgnore);
                    break;
                case "options":
                    operationIgnore.setOptions(pathIgnore);
                    break;
                case "patch":
                    operationIgnore.setPatch(pathIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" please referenced the documentation for supported entries.",
                            ignoresIt.getKey()));
            }

            this.globalIgnore.getPaths().put(pathIt.getKey(), operationIgnore);
        }
    }

    private void parameterParsing(Map.Entry<String, JsonNode> ignoreType, PathIgnore pathIgnore) {
        List<String> paramsIgnore = new ArrayList<>();

        if (ignoreType.getValue().elements().hasNext()) {
            for (Iterator<JsonNode> it = ignoreType.getValue().elements(); it.hasNext(); ) {
                paramsIgnore.add(it.next().asText());
            }
        }

        pathIgnore.setParameters(paramsIgnore);
    }

    private void responseParsing(Map.Entry<String, JsonNode> ignoreType, PathIgnore pathIgnore) throws SpecificationSupportException {
        ResponseIgnore responseIgnore = new ResponseIgnore();
        List<String> status = new ArrayList<>();

        if (ignoreType.getValue().elements().hasNext()) {
            for (Iterator<Map.Entry<String, JsonNode>> responseScope = ignoreType.getValue().fields(); responseScope.hasNext(); ) {
                Map.Entry<String, JsonNode> responseIgnoreItem = responseScope.next();

                switch (responseIgnoreItem.getKey()) {
                    case "status":
                        for (Iterator<JsonNode> it = responseIgnoreItem.getValue().elements(); it.hasNext(); ) {
                            status.add(it.next().asText());
                        }
                        responseIgnore.setStatus(status);
                        break;
                    case "info":
                        responseIgnore.setInfo(responseIgnoreItem.getValue().asText());
                        break;
                    default:
                        throw new SpecificationSupportException(String.format(
                                "Specification does not support value \"%s\" as response ignore type, please referenced the documentation for supported entries.",
                                responseIgnoreItem.getKey()));
                }
            }
        }

        pathIgnore.setResponseIgnore(responseIgnore);
    }

    private void requestParsing(Map.Entry<String, JsonNode> ignoreType, PathIgnore pathIgnore) throws SpecificationSupportException {

        RequestIgnore requestIgnore = new RequestIgnore();
        List<String> contentTypes = new ArrayList<>();

        if (ignoreType.getValue().elements().hasNext()) {
            for (Iterator<Map.Entry<String, JsonNode>> requestScope = ignoreType.getValue().fields(); requestScope.hasNext(); ) {
                Map.Entry<String, JsonNode> requestIgnoreItem = requestScope.next();

                switch (requestIgnoreItem.getKey()) {
                    case "content-type":
                        for (Iterator<JsonNode> it = requestIgnoreItem.getValue().elements(); it.hasNext(); ) {
                            contentTypes.add(it.next().asText());
                        }
                        requestIgnore.setContentType(contentTypes);
                        break;
                    default:
                        throw new SpecificationSupportException(String.format(
                                "Specification does not support value \"%s\" as request ignore type, please referenced the documentation for supported entries.",
                                requestIgnoreItem.getKey()));
                }
            }
        }

        pathIgnore.setRequestIgnore(requestIgnore);
    }


    private void validateVersion(Map.Entry<String, JsonNode> globalScope) throws InvalidVersionException {
        if (versions.contains(globalScope.getValue().asText())) {
            globalIgnore.setVersion(globalScope.getValue().asText());
        } else {
            throw new InvalidVersionException(String.format(
                    "Version \"%s\" that was specified is invalid.",
                    globalScope.getValue().asText()));
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
                        securityReqList.add(requirement.asText());
                    }
                    securityMap.put(securityReq.getKey(), securityReqList);
                }

                securityIgnore.setSecurities(securityMap);
            }

        }
        pathIgnore.setSecurityIgnore(securityIgnore);
    }
}
