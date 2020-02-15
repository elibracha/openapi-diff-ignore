package org.openapi.diff.ignore.processors;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.models.OpenApiIgnore;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;
import org.openapi.diff.ignore.models.validations.enums.HttpMethodSupport;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenApiPreprocessor {

    private OpenAPI originalOpenAPI;
    private OpenAPI codeOpenAPI;
    private OpenApiIgnore openApiIgnore;

    public void process() {
        List<String> supported = Arrays.stream(HttpMethodSupport.values())
                .map(HttpMethodSupport::getValue)
                .collect(Collectors.toList());

        for (Map.Entry<String, PathItem> entryOriginal : originalOpenAPI.getPaths().entrySet()) {
            for (Map.Entry<String, HttpMethodIgnore> entryIgnore : openApiIgnore.getIgnore().getPaths().getPaths().entrySet()) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                if (antPathMatcher.match(entryIgnore.getKey(), entryOriginal.getKey())) {
                    for (String method : supported) {
                        OperationIgnore operationIgnore = entryIgnore.getValue().checkIfIgnoreExist(method);
                        if (operationIgnore != null) {
                            Operation operation = getOperation(method, entryOriginal.getValue());
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    private Operation getOperation(String key, PathItem pathItem) {
        switch (key) {
            case SpecConstants.HttpMethods.POST:
                return pathItem.getPost();
            case SpecConstants.HttpMethods.GET:
                return pathItem.getGet();
            case SpecConstants.HttpMethods.PUT:
                return pathItem.getPut();
            case SpecConstants.HttpMethods.DELETE:
                return pathItem.getDelete();
            case SpecConstants.HttpMethods.HEAD:
                return pathItem.getHead();
            case SpecConstants.HttpMethods.OPTIONS:
                return pathItem.getOptions();
            case SpecConstants.HttpMethods.TRACE:
                return pathItem.getTrace();
            case SpecConstants.HttpMethods.PATCH:
                return pathItem.getPatch();
        }

        return null;
    }
}
