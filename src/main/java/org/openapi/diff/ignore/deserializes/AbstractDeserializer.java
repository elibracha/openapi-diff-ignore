package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;

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
        if (!node.isContainerNode()) {
            elem.setIgnoreAll(true);
        }

        return elem;
    }
}
