package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedOperation;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.OperationIgnore;

public class HttpMethodProcessor {
    private final RequestProcessor requestProcessor = new RequestProcessor();

    public void apply(HttpMethodIgnore httpMethodIgnore, ChangedOperation changedOperation) {
        String httpMethod = changedOperation.getHttpMethod().name().toLowerCase();
        OperationIgnore operationIgnore = httpMethodIgnore.checkIfIgnoreExist(httpMethod);

        if (operationIgnore != null) {
            if (operationIgnore.getRequest() != null && changedOperation.getRequestBody() != null) {
                boolean result = requestProcessor.apply(operationIgnore.getRequest(), changedOperation.getRequestBody());
                if (result) {
                    changedOperation.setRequestBody(null);
                }
            }
        }

        System.out.println();
    }
}
