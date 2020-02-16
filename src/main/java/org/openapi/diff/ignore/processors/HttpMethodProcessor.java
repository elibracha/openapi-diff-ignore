package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedOperation;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;

public class HttpMethodProcessor {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private final SecurityProcessor securityProcessor = new SecurityProcessor();

    public boolean apply(HttpMethodIgnore httpMethodIgnore, ChangedOperation changedOperation) {
        String httpMethod = changedOperation.getHttpMethod().name().toLowerCase();
        OperationIgnore operationIgnore = httpMethodIgnore.checkIfIgnoreExist(httpMethod);

        if (operationIgnore != null) {
            if (operationIgnore.getRequest() != null && changedOperation.getRequestBody() != null) {
                boolean requestResult = requestProcessor.apply(operationIgnore.getRequest(), changedOperation.getRequestBody());
                boolean securityResult = securityProcessor.apply(operationIgnore.getSecurity(), changedOperation.getSecurityRequirements());

                if (requestResult) {
                    changedOperation.setRequestBody(null);
                }

                if (securityResult) {
                    changedOperation.setSecurityRequirements(null);
                }
            }
        }

        return changedOperation.getRequestBody() == null &&
                changedOperation.getSecurityRequirements() == null &&
                changedOperation.getParameters() == null &&
                changedOperation.getApiResponses() == null;
    }
}
