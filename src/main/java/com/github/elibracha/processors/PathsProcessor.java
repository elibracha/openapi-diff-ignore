package com.github.elibracha.processors;

import com.github.elibracha.model.ChangedOperation;
import com.github.elibracha.models.ignore.PathsIgnore;
import org.springframework.util.AntPathMatcher;

public class PathsProcessor {

    private final HttpMethodProcessor httpMethodProcessor = new HttpMethodProcessor();

    public boolean apply(ChangedOperation changedOperation, PathsIgnore pathsIgnore,
                         AntPathMatcher antPathMatcher) {

        String pathUrl = changedOperation.getPathUrl();

        if (pathsIgnore != null) {
            for (String path : pathsIgnore.getPaths().keySet()) {
                boolean match = antPathMatcher.match(path, pathUrl);

                if (match) {
                    boolean result = httpMethodProcessor.apply(pathsIgnore.getPaths().get(path), changedOperation);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
