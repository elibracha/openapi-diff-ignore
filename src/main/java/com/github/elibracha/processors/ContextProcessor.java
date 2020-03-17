package com.github.elibracha.processors;


import com.github.elibracha.ContextMapKey;
import com.github.elibracha.models.OpenApiIgnore;
import com.github.elibracha.models.SpecConstants;
import com.qdesrame.openapi.diff.model.ChangedOpenApi;
import com.qdesrame.openapi.diff.model.ChangedOperation;
import lombok.Data;
import com.github.elibracha.exceptions.SpecificationSupportException;
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
import java.util.ArrayList;
import java.util.List;

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
        List<ChangedOperation> toRemove = new ArrayList<>();

        for (ChangedOperation changedOperation : changedOpenApi.getChangedOperations()) {
            boolean result = pathsProcessor.apply(changedOperation, openApiIgnore.getIgnore().getPaths(), antPathMatcher);
            if (result) {
                toRemove.add(changedOperation);
            }
        }

        changedOpenApi.getChangedOperations().removeAll(toRemove);
        return changedOpenApi;
    }
}
