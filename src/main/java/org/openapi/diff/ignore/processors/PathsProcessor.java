package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.model.ChangedOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.models.ignore.PathsIgnore;
import org.springframework.util.AntPathMatcher;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PathsProcessor {

    private HttpMethodProcessor httpMethodProcessor;

    public ChangedOpenApi apply(ChangedOperation changedOperation, PathsIgnore pathsIgnore,
                                AntPathMatcher antPathMatcher) {

        String pathUrl = changedOperation.getPathUrl();

        if (pathsIgnore != null) {
            for (String path : pathsIgnore.getPaths().keySet()) {
                boolean match = antPathMatcher.match(path, pathUrl);

                if (match) {
                    httpMethodProcessor.apply(pathsIgnore.getPaths().get(path), changedOperation);
                }
            }
        }

        return null;
    }
}
