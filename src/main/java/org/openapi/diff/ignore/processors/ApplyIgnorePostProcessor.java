package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.model.ChangedOperation;
import com.qdesrame.openapi.diff.model.ChangedParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.openapi.diff.ignore.models.IgnoreOpenApi;
import org.openapi.diff.ignore.models.ignore.PathIgnore;
import org.openapi.diff.ignore.models.ignore.PathOperationIgnore;

import java.util.ArrayList;
import java.util.List;

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

            boolean requestClear = false;
            boolean responseClear = false;
            boolean parametersClear = false;

            PathOperationIgnore pathOperationIgnore = ignoreOpenApi.getIgnore().getPaths().get(changedOperation.getPathUrl());

            if (pathOperationIgnore != null) {

                String httpMethod = changedOperation.getHttpMethod().name().toLowerCase();
                PathIgnore pathIgnore = pathOperationIgnore.getIgnore().checkIfIgnoreExist(httpMethod);

                if (pathIgnore != null) {

                    if (changedOperation.getParameters() != null) {

                        if (changedOperation.getParameters().getChanged() != null) {
                            List<ChangedParameter> changedParametersToRemove = new ArrayList<>();

                            for (String ignoreParameter : pathIgnore.getParameters()) {
                                for (ChangedParameter changedParameter : changedOperation.getParameters().getChanged()) {
                                    if (ignoreParameter.equals(changedParameter.getName())) {
                                        changedParametersToRemove.add(changedParameter);
                                    }
                                }
                            }

                            changedOperation.getParameters().getChanged().removeAll(changedParametersToRemove);
                        }

                        if (changedOperation.getParameters().getIncreased() != null) {
                            List<Parameter> increasedParametersToRemove = new ArrayList<>();

                            for (String ignoreParameter : pathIgnore.getParameters()) {
                                for (Parameter increasedParameter : changedOperation.getParameters().getIncreased()) {
                                    if (ignoreParameter.equals(increasedParameter.getName())) {
                                        increasedParametersToRemove.add(increasedParameter);
                                    }
                                }
                            }

                            changedOperation.getParameters().getIncreased().removeAll(increasedParametersToRemove);
                        }

                        if (changedOperation.getParameters().getMissing() != null) {
                            List<Parameter> missingParametersToRemove = new ArrayList<>();

                            for (String ignoreParameter : pathIgnore.getParameters()) {
                                for (Parameter missingParameter : changedOperation.getParameters().getMissing()) {
                                    if (ignoreParameter.equals(missingParameter.getName())) {
                                        missingParametersToRemove.add(missingParameter);
                                    }
                                }
                            }

                            changedOperation.getParameters().getMissing().removeAll(missingParametersToRemove);
                        }

                        if (changedOperation.getParameters().getMissing().size() == 0 &&
                                changedOperation.getParameters().getIncreased().size() == 0 &&
                                changedOperation.getParameters().getChanged().size() == 0) {
                            parametersClear = true;
                        }

                    } else {
                        parametersClear = true;
                    }
                    if (changedOperation.getRequestBody() != null) {

                        if (changedOperation.getRequestBody().getContent().getMissing() != null) {
                            for (String contentType : pathIgnore.getRequestIgnore().getContentType()) {
                                changedOperation.getRequestBody().getContent().getMissing().remove(contentType);
                            }
                        }

                        if (changedOperation.getRequestBody().getContent().getIncreased() != null) {
                            for (String contentType : pathIgnore.getRequestIgnore().getContentType()) {
                                changedOperation.getRequestBody().getContent().getIncreased().remove(contentType);
                            }
                        }

                        if (changedOperation.getRequestBody().getContent().getChanged() != null) {
                            for (String contentType : pathIgnore.getRequestIgnore().getContentType()) {
                                changedOperation.getRequestBody().getContent().getChanged().remove(contentType);
                            }
                        }

                        if (changedOperation.getRequestBody().getContent().getChanged().size() == 0 &&
                                changedOperation.getRequestBody().getContent().getIncreased().size() == 0 &&
                                changedOperation.getRequestBody().getContent().getMissing().size() == 0) {
                            requestClear = true;
                        }

                    } else {
                        requestClear = true;
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
                                changedOperation.getApiResponses().getChanged().size() == 0) {
                            responseClear = true;
                        }
                    } else {
                        responseClear = true;
                    }

                    if (requestClear && responseClear && parametersClear)
                        changedOperationsToRemove.add(changedOperation);
                }
            }
        }

        changedOpenApi.getChangedOperations().removeAll(changedOperationsToRemove);
        return changedOpenApi;
    }
}
