package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.exceptions.InvalidVersionException;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.*;

import java.io.IOException;
import java.util.*;

public class ContextDeserializer extends StdDeserializer<GlobalIgnore> {

    private GlobalIgnore globalIgnore;

    protected ContextDeserializer(Class<?> vc) {
        super(vc);
        this.globalIgnore = new GlobalIgnore();
    }

    @Override
    public GlobalIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        for (Iterator<Map.Entry<String, JsonNode>> globalIt = node.fields(); globalIt.hasNext(); ) {
            Map.Entry<String, JsonNode> globalScope = globalIt.next();

            switch (globalScope.getKey()) {
                case SpecConstants.RootIgnoreEntries.VERSION:
                    setGlobalVersion(globalScope);
                    break;
                case SpecConstants.RootIgnoreEntries.PROJECT:
                    setGlobalProject(globalScope);
                    break;
                case SpecConstants.RootIgnoreEntries.INFO:
                    setGlobalInfo(globalScope);
                    break;
                case SpecConstants.RootIgnoreEntries.PATHS:
                    setGlobalPaths(globalScope);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" please referenced the documentation for supported entries.",
                            globalScope.getKey()));
            }
        }
        return this.globalIgnore;
    }

    private void setGlobalPaths(Map.Entry<String, JsonNode> globalScope) throws SpecificationSupportException {
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
                case SpecConstants.PathIgnoresEntries.RESPONSE:
                    responseParsing(ignoreType, pathIgnore);
                    break;
                case SpecConstants.PathIgnoresEntries.REQUEST:
                    requestParsing(ignoreType, pathIgnore);
                    break;
                case SpecConstants.PathIgnoresEntries.SECURITY:
                    securityParsing(ignoreType, pathIgnore);
                    break;
                case SpecConstants.PathIgnoresEntries.PARAMETERS:
                    parameterParsing(ignoreType, pathIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as ignore type please referenced the documentation for supported entries.",
                            ignoreType.getKey()));
            }

            switch (ignoresIt.getKey()) {
                case SpecConstants.HttpMethods.POST:
                    operationIgnore.setPost(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.PUT:
                    operationIgnore.setPut(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.DELETE:
                    operationIgnore.setDelete(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.GET:
                    operationIgnore.setGet(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.TRACE:
                    operationIgnore.setTrace(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.HEAD:
                    operationIgnore.setHead(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.OPTIONS:
                    operationIgnore.setOptions(pathIgnore);
                    break;
                case SpecConstants.HttpMethods.PATCH:
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

    private void securityParsing(Map.Entry<String, JsonNode> ignoreType, PathIgnore pathIgnore) {
        SecurityIgnore securityIgnore = new SecurityIgnore();

        if (ignoreType.getValue().elements().hasNext()) {
            Map<String, SecurityOperationIgnore> securityMap = new HashMap<>();

            for (Iterator<JsonNode> it = ignoreType.getValue().elements(); it.hasNext(); ) {
                JsonNode securityNode = it.next();

                for (Iterator<Map.Entry<String, JsonNode>> securityScope = securityNode.fields(); securityScope.hasNext(); ) {
                    Map.Entry<String, JsonNode> securityReq = securityScope.next();

                    SecurityOperationIgnore securityOperationIgnore = new SecurityOperationIgnore();

                    for (Iterator<JsonNode> securityReqIt = securityReq.getValue().elements(); securityReqIt.hasNext(); ) {
                        JsonNode requirement = securityReqIt.next();
                        securityOperationIgnore.getSecurities().add(requirement.asText());
                    }

                    securityMap.put(securityReq.getKey(), securityOperationIgnore);
                }
            }
            securityIgnore.setSecurities(securityMap);
        }
        pathIgnore.setSecurityIgnore(securityIgnore);
    }

    private void setGlobalVersion(Map.Entry<String, JsonNode> globalScope) throws InvalidVersionException {
        if (SpecConstants.VERSIONS.contains(globalScope.getValue().asText())) {
            globalIgnore.setVersion(globalScope.getValue().asText());
        } else {
            throw new InvalidVersionException(String.format("Version \"%s\" that was specified is invalid.",
                    globalScope.getValue().asText()));
        }
    }

    private void setGlobalInfo(Map.Entry<String, JsonNode> globalScope) {
        this.globalIgnore.setProject(globalScope.getValue().asText());
    }

    private void setGlobalProject(Map.Entry<String, JsonNode> globalScope) {
        this.globalIgnore.setInfo(globalScope.getValue().asText());
    }
}
