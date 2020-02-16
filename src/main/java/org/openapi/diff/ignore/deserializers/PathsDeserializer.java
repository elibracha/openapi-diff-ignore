package org.openapi.diff.ignore.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.models.ignore.HttpMethodIgnore;
import org.openapi.diff.ignore.models.ignore.PathsIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PathsDeserializer extends AbstractDeserializer<PathsIgnore> {

    public PathsDeserializer() {
        super(PathsIgnore.class);
    }

    @Override
    public PathsIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode paths = jsonParser.getCodec().readTree(jsonParser);
        PathsIgnore pathsIgnore = (PathsIgnore) preProcess(new PathsIgnore(), paths);

        Map<String, HttpMethodIgnore> map = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = paths.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> pathScope = it.next();
            HttpMethodIgnore path = ObjectMapperFactory.createYaml().convertValue(pathScope.getValue(), HttpMethodIgnore.class);
            if (checkWildCards(pathScope.getKey()))
                for (String key : extractWildCards(pathScope.getKey()))
                    map.put(key, path);
            else {
                map.put(pathScope.getKey(), path);
            }
        }

        pathsIgnore.setPaths(map);

        return pathsIgnore;
    }
}
