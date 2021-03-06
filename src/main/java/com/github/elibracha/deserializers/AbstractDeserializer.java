package com.github.elibracha.deserializers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDeserializer<T> extends StdDeserializer<T> {

    public AbstractDeserializer(Class<?> vc) {
        super(vc);
    }

    public AbstractDeserializer(JavaType valueType) {
        super(valueType);
    }

    public AbstractDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    protected IgnoreElemnt preProcess(IgnoreElemnt elem, JsonNode node) {
        if (!node.isContainerNode() && node.asText().trim().equals("$")) {
            elem.setIgnoreAll(true);
        }

        return elem;
    }

    protected boolean checkWildCards(String key) {
        return key.contains(",") || key.equals("$");
    }

    protected List<String> extractWildCards(String key) {
        return key.equals("$") ? null : Arrays.stream(key.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
