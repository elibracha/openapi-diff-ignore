package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedApiResponse;
import com.qdesrame.openapi.diff.model.ChangedContent;
import com.qdesrame.openapi.diff.model.ChangedResponse;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.ContentSchema;
import org.openapi.diff.ignore.models.ignore.ResponseIgnore;

import java.util.Map;

public class ResponseProcessor {
    public boolean apply(ResponseIgnore response, ChangedApiResponse apiResponses) {
        if (apiResponses.getChanged() != null) {
            for (Map.Entry<String, ChangedResponse> entry : apiResponses.getChanged().entrySet()) {
                if (response.getResponse() != null && response.getResponse().getStatus() != null) {
                    processStatus(entry.getValue(), response.getResponse().getStatus().get(entry.getKey()));
                }
            }
        }
        return false;
    }

    private boolean processStatus(ChangedResponse changedResponse, Content contentIgnore) {

        for (Map.Entry<String, ContentSchema> entry : contentIgnore.getContentSchemas().entrySet()) {
            ContentSchema contentSchema = contentIgnore.getContentSchemas().get(entry.getKey());

            if (contentSchema != null && changedResponse.getContent() != null) {
                processContent(changedResponse.getContent(), contentSchema);
            }
        }

        return false;
    }

    private boolean processContent(ChangedContent content, ContentSchema contentSchema) {
        
        return false;
    }
}
