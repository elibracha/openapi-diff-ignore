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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.System.exit;

public class IgnoreProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final static String DEFAULT_SEARCH = ".diffignore";
    private final static String URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private ContextMapKey<String, String> mapKey;
    private List<String> ignoresPaths;

    public IgnoreProcessor() {
        this.mapKey = new ContextMapKey<>();
        this.ignoresPaths = Collections.singletonList(DEFAULT_SEARCH);
    }

    public IgnoreProcessor(String... ignoresPaths) {
        this.mapKey = new ContextMapKey<>();
        this.ignoresPaths = Arrays.asList(ignoresPaths);
    }

    public List<IgnoreOpenApi> processIgnore() {
        List<IgnoreOpenApi> ignores = new ArrayList<>();

        for (String path : this.ignoresPaths) {
            boolean result = false;
            if (path.matches(URL_PATTERN)) {
                try (InputStream inputStream = new URL(path).openStream()) {
                    Yaml yaml = new Yaml();
                    result = this.mapKey.load(yaml.load(inputStream));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    exit(1);
                }
            } else {
                try (InputStream inputStream = new FileInputStream(new File(path))) {
                    Yaml yaml = new Yaml();
                    result = this.mapKey.load(yaml.load(inputStream));
                } catch (IOException | YAMLException e) {
                    log.error(e.getMessage());
                    exit(1);
                }
            }

            ignores.add(new IgnoreOpenApi(this.mapKey.getGlobalIgnore()).setValidIgnore(result));
        }

        return ignores;
    }

    public ContextMapKey<String, String> getMapKey() {
        return mapKey;
    }
}
