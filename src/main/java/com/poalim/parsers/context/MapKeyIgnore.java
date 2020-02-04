package com.poalim.parsers.context;

import java.util.HashMap;
import java.util.Map;

public class MapKeyIgnore<K, V> {

    private Map<K, V> mapkey;

    public MapKeyIgnore(){
        this.mapkey = new HashMap<>();
    }

    public MapKeyIgnore<K, V> load(Map<K, V> map){
        this.mapkey.putAll(map);
        return this;
    }
}
