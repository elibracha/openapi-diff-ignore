package org.openapi.diff.ignore;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openapi.diff.ignore.context.ContextDeserializer;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;
import org.openapi.diff.ignore.processors.ValidationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class ContextMapKey<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Map<K, V> mapKey;
    private ContextIgnore contextIgnore;
    private ValidationProcessor<K, V> validationProcessor;

    public ContextMapKey() {
        this.mapKey = new HashMap<>();
        this.contextIgnore = new ContextIgnore();
        this.validationProcessor = new ValidationProcessor<>();
    }

    public ContextIgnore getContextIgnore() {
        return contextIgnore;
    }

    public boolean load(Map<K, V> map) {
        this.mapKey.putAll(map);
        return this.buildMap();
    }

    private boolean buildMap() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(ContextIgnore.class, new ContextDeserializer(ContextIgnore.class));
        mapper.registerModule(module);

        try {
            if (validationProcessor.validate(this.mapKey)) {
                this.contextIgnore = mapper.convertValue(this.mapKey, ContextIgnore.class);
                return true;
            }
        } catch (IllegalArgumentException e) {
            log.error(String.format("NOTICE! please save this trace if you need support:%s%s",
                    System.lineSeparator() + System.lineSeparator(),
                    e.getMessage()));
        }
        return false;
    }
}
