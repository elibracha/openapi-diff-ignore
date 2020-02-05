package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openapi.diff.ignore.models.ignore.GlobalIgnore;
import org.openapi.diff.ignore.processors.ValidationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class MapKeyIgnore<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Map<K, V> mapKey;
    private GlobalIgnore globalIgnore;
    private ValidationProcessor<K, V> validationProcessor;

    public MapKeyIgnore() {
        this.mapKey = new HashMap<>();
        this.globalIgnore = new GlobalIgnore();
        this.validationProcessor = new ValidationProcessor<>();
    }

    public void load(Map<K, V> map) {
        this.mapKey.putAll(map);
        this.buildMap();
    }

    private void buildMap() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try {
            if (validationProcessor.validate(this.mapKey)) {
                this.globalIgnore = mapper.convertValue(this.mapKey, GlobalIgnore.class);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }
}
