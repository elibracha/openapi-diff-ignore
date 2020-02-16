package org.openapi.diff.ignore.processors;


import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.model.ChangedOperation;
import lombok.Data;
import org.openapi.diff.ignore.ContextMapKey;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.OpenApiIgnore;
import org.openapi.diff.ignore.models.SpecConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

import static java.lang.System.exit;

@Data
public class ContextProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final ContextMapKey<String, String> mapKey = new ContextMapKey<>();
    private final PathsProcessor pathsProcessor = new PathsProcessor();
    private String ignorePath;

    public ContextProcessor() {
        this.ignorePath = SpecConstants.DEFAULT_SEARCH;
    }

    public ContextProcessor(String ignorePath) {
        this.ignorePath = ignorePath;
    }

    public ChangedOpenApi process(ChangedOpenApi changedOpenApi) throws SpecificationSupportException {

        boolean result = false;

        try (InputStream inputStream = new FileInputStream(new File(ignorePath))) {

            Yaml yaml = new Yaml();
            result = mapKey.load(yaml.load(inputStream));

        } catch (IOException | YAMLException e) {
            log.error(e.getMessage());
            exit(1);
        }


        OpenApiIgnore openApiIgnore = new OpenApiIgnore(result, mapKey.getContextIgnore());

        return apply(changedOpenApi, openApiIgnore);
    }

    private ChangedOpenApi apply(ChangedOpenApi changedOpenApi, OpenApiIgnore openApiIgnore) throws SpecificationSupportException {

        if (!openApiIgnore.isValidIgnore())
            throw new SpecificationSupportException("Invalid Ignore");

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        for (ChangedOperation changedOperation : changedOpenApi.getChangedOperations()) {
            pathsProcessor.apply(changedOperation, openApiIgnore.getIgnore().getPaths(), antPathMatcher);
        }

        return changedOpenApi;
    }
}
