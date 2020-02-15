package org.openapi.diff.ignore.processors;


import org.openapi.diff.ignore.ContextMapKey;
import org.openapi.diff.ignore.models.OpenApiIgnore;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

import static java.lang.System.exit;

public class ContextProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final ContextMapKey<String, String> mapKey = new ContextMapKey<>();
    private String ignorePath;

    public ContextProcessor() {
        this.ignorePath = SpecConstants.DEFAULT_SEARCH;
    }

    public ContextProcessor(String ignorePath) {
        this.ignorePath = ignorePath;
    }

    public OpenApiIgnore processIgnore() {

        boolean result = false;

        try (InputStream inputStream = new FileInputStream(new File(ignorePath))) {

            Yaml yaml = new Yaml();
            result = this.mapKey.load(yaml.load(inputStream));

        } catch (IOException | YAMLException e) {
            log.error(e.getMessage());
            exit(1);
        }


        ContextIgnore contextIgnore = this.mapKey.getContextIgnore();
        return new OpenApiIgnore(result, contextIgnore);
    }

    public ContextMapKey<String, String> getMapKey() {
        return mapKey;
    }
}
