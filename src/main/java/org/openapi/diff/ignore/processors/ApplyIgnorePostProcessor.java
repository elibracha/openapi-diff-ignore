package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedMediaType;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.model.ChangedOperation;
import org.openapi.diff.ignore.models.IgnoreOpenApi;
import org.openapi.diff.ignore.models.ignore.PathIgnore;
import org.openapi.diff.ignore.models.ignore.PathOperationIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplyIgnorePostProcessor {

    private ChangedOpenApi changedOpenApi;
    private IgnoreOpenApi ignoreOpenApi;
    private List<ChangedOperation> changedOperationsToRemove;

    public ApplyIgnorePostProcessor(ChangedOpenApi changedOpenApi, IgnoreOpenApi ignoreOpenApi) {
        this.changedOpenApi = changedOpenApi;
        this.ignoreOpenApi = ignoreOpenApi;
        this.changedOperationsToRemove = new ArrayList<>();
    }

    public ChangedOpenApi applyIgnore() {

        for (ChangedOperation changedOperation : this.changedOpenApi.getChangedOperations()) {
            PathOperationIgnore pathOperationIgnore = ignoreOpenApi.getIgnore().getPaths().get(changedOperation.getPathUrl());
            if (pathOperationIgnore != null) {
                String httpMethod = changedOperation.getHttpMethod().name().toLowerCase();

                PathIgnore pathIgnore = pathOperationIgnore.getIgnore().checkIfIgnoreExist(httpMethod);

                if (pathIgnore != null) {

                    if (changedOperation.getRequestBody() != null) {

                        for (Map.Entry<String, ChangedMediaType> changed : changedOperation.getRequestBody().getContent().getChanged().entrySet()) {
                            if (pathIgnore.getRequestIgnore().getContentType().contains(changed.getKey())) {
                                changedOperationsToRemove.add(changedOperation);
                            }
                        }
                    }

                    if (changedOperation.getApiResponses() != null) {
                        if (changedOperation.getApiResponses().getMissing() != null) {
                            for (String status : pathIgnore.getResponseIgnore().getStatus())
                                changedOperation.getApiResponses().getMissing().remove(status);
                        }

                        if (changedOperation.getApiResponses().getChanged() != null) {
                            for (String status : pathIgnore.getResponseIgnore().getStatus())
                                changedOperation.getApiResponses().getChanged().remove(status);
                        }

                        if (changedOperation.getApiResponses().getIncreased() != null) {
                            for (String status : pathIgnore.getResponseIgnore().getStatus())
                                changedOperation.getApiResponses().getIncreased().remove(status);
                        }

                        if (changedOperation.getApiResponses().getIncreased().size() == 0 &&
                                changedOperation.getApiResponses().getMissing().size() == 0 &&
                                changedOperation.getApiResponses().getChanged().size() == 0)
                            changedOperationsToRemove.add(changedOperation);
                    }
                }
            }
        }

        changedOpenApi.getChangedOperations().removeAll(changedOperationsToRemove);
        return changedOpenApi;
    }
}
