package com.github.elibracha.processors;

import com.github.elibracha.models.ignore.Content;
import com.github.elibracha.models.ignore.ContentSchema;
import com.github.elibracha.models.ignore.RequestIgnore;
import com.github.elibracha.model.Changed;
import com.github.elibracha.model.ChangedMediaType;
import com.github.elibracha.model.ChangedRequestBody;
import io.swagger.v3.oas.models.media.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestProcessor {

    public boolean apply(RequestIgnore requestIgnore, ChangedRequestBody requestBody) {
        List<String> changeToRemove = new ArrayList<>();
        List<String> missingAndIncreaseToRemove = new ArrayList<>();

        if (requestIgnore.isIgnoreAll())
            return true;

        if (requestBody.getContent() != null) {
            for (Map.Entry<String, ChangedMediaType> entry : requestBody.getContent().getChanged().entrySet()) {
                boolean result = processMediaTypeChanged(entry.getKey(), entry.getValue(), requestIgnore.getContent());
                if (result)
                    changeToRemove.add(entry.getKey());
            }

            for (Map.Entry<String, MediaType> entry : requestBody.getContent().getMissing().entrySet()) {
                boolean result = processMediaTypeMissingOrIncrease(entry.getKey(), entry.getValue(), requestIgnore.getContent());
                if (result)
                    missingAndIncreaseToRemove.add(entry.getKey());
            }

            for (Map.Entry<String, MediaType> entry : requestBody.getContent().getIncreased().entrySet()) {
                boolean result = processMediaTypeMissingOrIncrease(entry.getKey(), entry.getValue(), requestIgnore.getContent());
                if (result)
                    missingAndIncreaseToRemove.add(entry.getKey());
            }

            requestBody.getContent().getChanged().keySet().removeAll(changeToRemove);
            requestBody.getContent().getIncreased().keySet().removeAll(missingAndIncreaseToRemove);
            requestBody.getContent().getMissing().keySet().removeAll(missingAndIncreaseToRemove);

            return (requestBody.getContent().getChanged() == null || requestBody.getContent().getChanged().size() == 0) &&
                    (requestBody.getContent().getMissing() == null || requestBody.getContent().getMissing().size() == 0) &&
                    (requestBody.getContent().getIncreased() == null || requestBody.getContent().getIncreased().size() == 0);
        }

        return true;
    }

    private boolean processMediaTypeMissingOrIncrease(String key, MediaType value, Content content) {

        if (content != null)
            for (Map.Entry<String, ContentSchema> ignoreEntry : content.getContentSchemas().entrySet()) {
                if (ignoreEntry.getKey().matches(key)) {
                    if (ignoreEntry.getValue().isIgnoreAll() || ignoreEntry.getValue().getSchema().getProperties().contains(value.getSchema().getName())) {
                        return true;
                    }
                }
            }
        return false;
    }

    private boolean processMediaTypeChanged(String key, ChangedMediaType value, Content content) {

        for (Map.Entry<String, ContentSchema> entry : content.getContentSchemas().entrySet()) {
            if (entry.getKey().matches(key)) {
                if (value.getChangedElements().size() > 0)
                    for (Changed changedMediaType : value.getChangedElements()) {
                        if (entry.getValue().isIgnoreAll()) {
                            return true;
                        }

                        if (changedMediaType.isDifferent()) {
                            System.out.println(); //TODO: fix to clean up properly
                        }
                    }
            }
        }

        return false;
    }
}
