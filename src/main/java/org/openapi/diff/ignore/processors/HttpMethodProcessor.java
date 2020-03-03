package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedOperation;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;

public class HttpMethodProcessor {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private final SecurityProcessor securityProcessor = new SecurityProcessor();
    private final ParameterProcessor parameterProcessor = new ParameterProcessor();
    private final ResponseProcessor responseProcessor = new ResponseProcessor();

    public boolean apply(HttpMethodIgnore httpMethodIgnore, ChangedOperation changedOperation) {
        String httpMethod = changedOperation.getHttpMethod().name().toLowerCase();
        OperationIgnore operationIgnore = httpMethodIgnore.checkIfIgnoreExist(httpMethod);

        if ((operationIgnore != null && operationIgnore.isIgnoreAll()) || httpMethodIgnore.isIgnoreAll()) {
            return true;
        }

        if (operationIgnore != null) {
            if (operationIgnore.getRequest() != null && changedOperation.getRequestBody() != null) {
                boolean requestResult = requestProcessor.apply(operationIgnore.getRequest(), changedOperation.getRequestBody());
                if (requestResult) {
                    changedOperation.setRequestBody(null);
                }

            }
            if (operationIgnore.getSecurity() != null && changedOperation.getSecurityRequirements() != null) {
                boolean securityResult = securityProcessor.apply(operationIgnore.getSecurity(), changedOperation.getSecurityRequirements());
                if (securityResult) {
                    changedOperation.setSecurityRequirements(null);
                }
            }
            if (operationIgnore.getParameters() != null && changedOperation.getParameters() != null) {
                boolean parameterResult = parameterProcessor.apply(operationIgnore.getParameters(), changedOperation.getParameters());
                if (parameterResult) {
                    changedOperation.setParameters(null);
                }
            }
            if (operationIgnore.getResponse() != null && changedOperation.getApiResponses() != null) {
                boolean responseResult = responseProcessor.apply(operationIgnore.getResponse(), changedOperation.getApiResponses());
                if (responseResult) {
                    changedOperation.setApiResponses(null);
                }
            }
        }

        return changedOperation.getRequestBody() == null &&
                changedOperation.getSecurityRequirements() == null &&
                changedOperation.getParameters() == null &&
                changedOperation.getApiResponses() == null;
    }
}
