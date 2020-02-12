package org.openapi.diff.ignore.processors;


import org.openapi.diff.ignore.context.ContextMapKey;
import org.openapi.diff.ignore.models.IgnoreOpenApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;

import static java.lang.System.exit;

public class IgnoreProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final static String DEFAULT_SEARCH = ".diffignore";
    private final static String URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private ContextMapKey<String, String> mapKey;
    private String ignorePath;

    public IgnoreProcessor() {
        this.mapKey = new ContextMapKey<>();
        this.ignorePath = DEFAULT_SEARCH;
    }

    public IgnoreProcessor(String ignorePath) {
        this.mapKey = new ContextMapKey<>();
        this.ignorePath = ignorePath;
    }

    public IgnoreOpenApi processIgnore() {

        boolean result = false;
        if (ignorePath.matches(URL_PATTERN)) {
            try (InputStream inputStream = new URL(ignorePath).openStream()) {
                Yaml yaml = new Yaml();
                result = this.mapKey.load(yaml.load(inputStream));
            } catch (IOException e) {
                log.error(e.getMessage());
                exit(1);
            }
        } else {
            try (InputStream inputStream = new FileInputStream(new File(ignorePath))) {
                Yaml yaml = new Yaml();
                result = this.mapKey.load(yaml.load(inputStream));
            } catch (IOException | YAMLException e) {
                log.error(e.getMessage());
                exit(1);
            }
        }

        return new IgnoreOpenApi(this.mapKey.getContextIgnore()).setValidIgnore(result);
    }

    public ContextMapKey<String, String> getMapKey() {
        return mapKey;
    }
}
