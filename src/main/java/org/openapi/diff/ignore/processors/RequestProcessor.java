package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.Changed;
import com.qdesrame.openapi.diff.model.ChangedMediaType;
import com.qdesrame.openapi.diff.model.ChangedRequestBody;
import org.openapi.diff.ignore.models.ignore.Content;
import org.openapi.diff.ignore.models.ignore.ContentSchema;
import org.openapi.diff.ignore.models.ignore.RequestIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestProcessor {

    public boolean apply(RequestIgnore requestIgnore, ChangedRequestBody requestBody) {
        List<String> toRemove = new ArrayList<>();

        if (requestBody.getContent() != null && requestBody.getContent().getChanged().size() > 0) {
            for (Map.Entry<String, ChangedMediaType> entry : requestBody.getContent().getChanged().entrySet()) {
                boolean result = processMediaType(entry.getKey(), entry.getValue(), requestIgnore.getContent());
                if (result)
                    toRemove.add(entry.getKey());
            }
            requestBody.getContent().getChanged().keySet().removeAll(toRemove);

            return requestBody.getContent().getChanged().size() == 0 &&
                    requestBody.getContent().getMissing().size() == 0 &&
                    requestBody.getContent().getIncreased().size() == 0;
        }

        return false;
    }

    private boolean processMediaType(String key, ChangedMediaType value, Content content) {

        for (Map.Entry<String, ContentSchema> entry : content.getContentSchemas().entrySet()) {
            if (entry.getKey().matches(key)) {
                if (value.getChangedElements().size() > 0)
                    for (Changed changedMediaType : value.getChangedElements()) {
                        if (entry.getValue().isIgnoreAll()) {
                            return true;
                        }

                        if (changedMediaType.isDifferent()) {
                        }
                    }
            }
        }

        return false;
    }
}
