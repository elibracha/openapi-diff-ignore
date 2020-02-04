package com.poalim.parsers.processors;


import com.poalim.parsers.context.MapKeyIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

public class IgnoreProcessor {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final static String DEFAULT_SEARCH_DIRECTORY = ".";

    private MapKeyIgnore<String, String> mapKey;
    private String ignorePath;

    public IgnoreProcessor() {
        this.mapKey = new MapKeyIgnore<>();
        this.ignorePath = DEFAULT_SEARCH_DIRECTORY;
    }

    public IgnoreProcessor(String path) {
        this.mapKey = new MapKeyIgnore<>();
        this.ignorePath = path;
    }

    public void processIgnore() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.ignorePath)) {
            Yaml yaml = new Yaml();
            this.mapKey.load(yaml.load(inputStream));
        } catch (IOException | YAMLException e) {
            log.error(e.getMessage());
        }
    }

    public MapKeyIgnore<String, String> getMapKey() {
        return mapKey;
    }
}
