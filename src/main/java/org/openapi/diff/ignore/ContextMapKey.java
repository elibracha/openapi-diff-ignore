package org.openapi.diff.ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class ContextMapKey<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Map<K, V> mapKey;
    private ContextIgnore contextIgnore;

    public ContextMapKey() {
        this.mapKey = new HashMap<>();
        this.contextIgnore = new ContextIgnore();
    }

    public ContextIgnore getContextIgnore() {
        return contextIgnore;
    }

    public boolean load(Map<K, V> map) {
        this.mapKey.putAll(map);
        return this.buildMap();
    }

    private boolean buildMap() {
        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();

        try {
            this.contextIgnore = objectMapper.convertValue(this.mapKey, ContextIgnore.class);
            return true;
        } catch (IllegalArgumentException e) {
            log.error(String.format("NOTICE! please save this trace if you need support:%s%s",
                    System.lineSeparator() + System.lineSeparator(),
                    e.getMessage()));
        }
        return false;
    }
}
