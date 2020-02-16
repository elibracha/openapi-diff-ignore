package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedApiResponse;
import com.qdesrame.openapi.diff.model.ChangedMediaType;
import com.qdesrame.openapi.diff.model.ChangedResponse;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.ContentSchema;
import org.openapi.diff.ignore.models.ignore.ResponseIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseProcessor {
    public boolean apply(ResponseIgnore response, ChangedApiResponse apiResponses) {
        List<String> changeToRemove = new ArrayList<>();

        if (apiResponses.getChanged() != null) {
            for (Map.Entry<String, ChangedResponse> entry : apiResponses.getChanged().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    boolean result = processStatusChange(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                    if (result)
                        changeToRemove.add(entry.getKey());
                }
            }

            for (Map.Entry<String, ApiResponse> entry : apiResponses.getMissing().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    boolean result = processStatusMissingOrIncrease(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                    if (result)
                        changeToRemove.add(entry.getKey());
                }
            }

            for (Map.Entry<String, ApiResponse> entry : apiResponses.getIncreased().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    boolean result = processStatusMissingOrIncrease(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                    if (result)
                        changeToRemove.add(entry.getKey());
                }
            }
            apiResponses.getChanged().keySet().removeAll(changeToRemove);
        }

        return (apiResponses.getIncreased() == null ||
                apiResponses.getIncreased().size() == 0) &&
                (apiResponses.getMissing() == null ||
                        apiResponses.getMissing().size() == 0) &&
                (apiResponses.getChanged() == null ||
                        apiResponses.getChanged().size() == 0);
    }

    private boolean processStatusMissingOrIncrease(ApiResponse apiResponse, Content contentIgnore) {
        return contentIgnore != null && contentIgnore.isNewContent();
    }

    private boolean processStatusChange(ChangedResponse changedResponse, Content contentIgnore) {

        List<String> toRemove = new ArrayList<>();

        if (contentIgnore != null && changedResponse.getContent() != null) {
            for (Map.Entry<String, ChangedMediaType> entry : changedResponse.getContent().getChanged().entrySet()) {
                boolean result = processContent(entry.getKey(), entry.getValue(), contentIgnore.getContentSchemas());
                if (result)
                    toRemove.add(entry.getKey());
            }
        }

        changedResponse.getContent().getChanged().keySet().removeAll(toRemove);

        return (changedResponse.getContent().getIncreased() == null ||
                changedResponse.getContent().getIncreased().size() == 0) &&
                (changedResponse.getContent().getMissing() == null ||
                        changedResponse.getContent().getMissing().size() == 0) &&
                (changedResponse.getContent().getChanged() == null ||
                        changedResponse.getContent().getChanged().size() == 0);
    }

    private boolean processContent(String key, ChangedMediaType content, Map<String, ContentSchema> contentSchemas) {
        if (contentSchemas == null)
            return false;

        ContentSchema contentSchema = contentSchemas.get(key);
        List<String> increaseToRemove = new ArrayList<>();
        List<String> missingToRemove = new ArrayList<>();

        for (Map.Entry<String, Schema> entry : content.getSchema().getIncreasedProperties().entrySet()) {
            if (contentSchema.getSchema().getProperties().contains(entry.getKey())) {
                increaseToRemove.add(entry.getKey());
            }
        }

        for (Map.Entry<String, Schema> entry : content.getSchema().getMissingProperties().entrySet()) {
            if (contentSchema.getSchema().getProperties().contains(entry.getKey())) {
                missingToRemove.add(entry.getKey());
            }
        }

        content.getSchema().getIncreasedProperties().keySet().removeAll(increaseToRemove);
        content.getSchema().getMissingProperties().keySet().removeAll(missingToRemove);

        return content.getSchema().getMissingProperties() == null ||
                content.getSchema().getMissingProperties().size() == 0;
    }
}
