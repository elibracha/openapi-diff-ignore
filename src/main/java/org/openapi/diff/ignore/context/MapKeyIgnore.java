package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openapi.diff.ignore.models.ignore.GlobalIgnore;

import java.util.HashMap;
import java.util.Map;

public class MapKeyIgnore<K, V> {

    private final static String VERSION_SUPPORT = "1.0.0";

    private Map<K, V> mapKey;
    private GlobalIgnore globalIgnore;

    public MapKeyIgnore() {
        this.mapKey = new HashMap<>();
        this.globalIgnore = new GlobalIgnore();
    }

    public MapKeyIgnore<K, V> load(Map<K, V> map) {
        this.mapKey.putAll(map);
        this.buildMap();
        return this;
    }

    private void buildMap() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.globalIgnore = mapper.convertValue(this.mapKey, GlobalIgnore.class);
    }
}
