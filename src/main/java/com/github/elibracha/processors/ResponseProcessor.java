package com.github.elibracha.processors;

import com.github.elibracha.models.ignore.Content;
import com.github.elibracha.models.ignore.ContentSchema;
import com.github.elibracha.models.ignore.ResponseIgnore;
import com.github.elibracha.model.ChangedApiResponse;
import com.github.elibracha.model.ChangedMediaType;
import com.github.elibracha.model.ChangedResponse;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseProcessor {
    public boolean apply(ResponseIgnore response, ChangedApiResponse apiResponses) {
        List<String> changeToRemove = new ArrayList<>();
        List<String> missingToRemove = new ArrayList<>();
        List<String> increaseToRemove = new ArrayList<>();

        if (response.isIgnoreAll()) {
            return true;
        }

        if (apiResponses.getChanged() != null) {
            for (Map.Entry<String, ChangedResponse> entry : apiResponses.getChanged().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    boolean result = processStatusChange(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                    if (result)
                        changeToRemove.add(entry.getKey());
                }
            }
            apiResponses.getChanged().keySet().removeAll(changeToRemove);
        }
        if (apiResponses.getMissing() != null) {
            for (Map.Entry<String, ApiResponse> entry : apiResponses.getMissing().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    boolean result = processStatusMissingOrIncrease(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                    if (result)
                        missingToRemove.add(entry.getKey());
                }
            }
            apiResponses.getMissing().keySet().removeAll(missingToRemove);
        }
        if (apiResponses.getIncreased() != null) {
            for (Map.Entry<String, ApiResponse> entry : apiResponses.getIncreased().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    boolean result = processStatusMissingOrIncrease(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                    if (result)
                        increaseToRemove.add(entry.getKey());
                }
            }
            apiResponses.getIncreased().keySet().removeAll(increaseToRemove);
        }

        return (apiResponses.getIncreased() == null ||
                apiResponses.getIncreased().size() == 0) &&
                (apiResponses.getMissing() == null ||
                        apiResponses.getMissing().size() == 0) &&
                (apiResponses.getChanged() == null ||
                        apiResponses.getChanged().size() == 0);
    }

    private boolean processStatusMissingOrIncrease(ApiResponse apiResponse, Content contentIgnore) {
        List<String> toRemove = new ArrayList<>();

        if (contentIgnore != null && contentIgnore.isIgnoreAll()) {
            return true;
        }

        if (contentIgnore != null && apiResponse.getContent() != null) {
            for (Map.Entry<String, MediaType> entry : apiResponse.getContent().entrySet()) {
                boolean result = processContentMissingOrIncreased(entry.getKey(), entry.getValue(), contentIgnore.getContentSchemas());
                if (result)
                    toRemove.add(entry.getKey());
            }
            apiResponse.getContent().keySet().removeAll(toRemove);
        }
        return (contentIgnore != null && contentIgnore.isNewContent()) || (contentIgnore != null && contentIgnore.isIgnoreAll())
                || ((apiResponse.getContent() == null || apiResponse.getContent().size() == 0) && apiResponse.getDescription() == null &&
                apiResponse.getHeaders() == null && apiResponse.get$ref() == null && apiResponse.getExtensions() == null && apiResponse.getLinks() == null);
    }

    private boolean processStatusChange(ChangedResponse changedResponse, Content contentIgnore) {

        List<String> toRemove = new ArrayList<>();

        if (contentIgnore != null && contentIgnore.isIgnoreAll()) {
            return true;
        }

        if (contentIgnore != null && changedResponse.getContent() != null) {
            for (Map.Entry<String, ChangedMediaType> entry : changedResponse.getContent().getChanged().entrySet()) {
                boolean result = processContentChanged(entry.getKey(), entry.getValue(), contentIgnore.getContentSchemas());
                if (result)
                    toRemove.add(entry.getKey());
            }
        }

        if(changedResponse.getContent() != null)
        	changedResponse.getContent().getChanged().keySet().removeAll(toRemove);
            else {
                return true;
        }
        return (changedResponse.getContent().getIncreased() == null ||
                changedResponse.getContent().getIncreased().size() == 0) &&
                (changedResponse.getContent().getMissing() == null ||
                        changedResponse.getContent().getMissing().size() == 0) &&
                (changedResponse.getContent().getChanged() == null ||
                        changedResponse.getContent().getChanged().size() == 0);
    }

    private boolean processContentMissingOrIncreased(String key, MediaType content, Map<String, ContentSchema> contentSchemas) {
        if (contentSchemas == null)
            return false;

        ContentSchema contentSchema = contentSchemas.get(key);

        if (contentSchema != null)
            if (contentSchema.getSchema().getProperties().contains(content.getSchema().getName()))
                content.setSchema(null);

        return content.getSchema() == null;
    }

    private boolean processContentChanged(String key, ChangedMediaType content, Map<String, ContentSchema> contentSchemas) {
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

        if(content.getSchema() != null) {
	        content.getSchema().getIncreasedProperties().keySet().removeAll(increaseToRemove);
	        content.getSchema().getMissingProperties().keySet().removeAll(missingToRemove);
        }
        
        return content.getSchema().getMissingProperties() == null ||
                content.getSchema().getMissingProperties().size() == 0;
    }
}
