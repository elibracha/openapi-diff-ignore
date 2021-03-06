package com.github.elibracha.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.elibracha.ObjectMapperFactory;
import com.github.elibracha.models.SpecConstants;
import com.github.elibracha.models.ignore.ContextIgnore;
import lombok.extern.slf4j.Slf4j;
import com.github.elibracha.exceptions.SpecificationSupportException;
import com.github.elibracha.models.ignore.PathsIgnore;
import com.github.elibracha.processors.ValidationProcessor;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class ContextDeserializer extends StdDeserializer<ContextIgnore> {

    private final ValidationProcessor validationProcessor = new ValidationProcessor();

    private PathsIgnore pathsIgnore;
    private PathsIgnore pathsIgnoreExtended;

    public ContextDeserializer() {
        super(ContextIgnore.class);
    }

    @Override
    public ContextIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
        ContextIgnore contextIgnore = new ContextIgnore();

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        if (validationProcessor.validate(root))
            for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> globalScope = it.next();

                switch (globalScope.getKey()) {
                    case SpecConstants.ContextEntries.VERSION:
                        contextIgnore.setVersion(globalScope.getValue().asText());
                        break;
                    case SpecConstants.ContextEntries.PROJECT:
                        contextIgnore.setProject(globalScope.getValue().asText());
                        break;
                    case SpecConstants.ContextEntries.INFO:
                        contextIgnore.setInfo(globalScope.getValue().asText());
                        break;
                    case SpecConstants.ContextEntries.PATHS:
                        pathsIgnore = objectMapper.convertValue(globalScope.getValue(), PathsIgnore.class);
                        extendPostProcess();
                        contextIgnore.setPaths(pathsIgnore);
                        break;
                    case SpecConstants.ContextEntries.EXTENDS:
                        extend(globalScope);
                        break;
                    default:
                        throw new SpecificationSupportException(String.format(
                                "Specification does not support value \"%s\", please referenced the documentation for supported entries.",
                                globalScope.getKey()));
                }
            }

        return contextIgnore;
    }

    private void extendPostProcess() {
        if (pathsIgnoreExtended != null) {
            HashSet<String> keys = new HashSet<>(pathsIgnoreExtended.getPaths().keySet());
            HashSet<String> deletedKeys = new HashSet<>();

            for (String key : keys) {
                if (pathsIgnore.getPaths().containsKey(key)) {
                    pathsIgnoreExtended.getPaths().remove(key);
                    deletedKeys.add(key);
                } else {
                    pathsIgnore.getPaths().put(key, pathsIgnoreExtended.getPaths().get(key));
                }
            }

            if (!deletedKeys.isEmpty()) {
                log.warn(String.format("You are overriding default values from the ignore files you extended. (%s)", deletedKeys));
            }
        }
    }

    private void extend(Map.Entry<String, JsonNode> globalScope) throws IOException {
        if (this.pathsIgnore != null) {
            throw new SpecificationSupportException("extends key must be above paths ignore declaration.");
        }

        try(InputStream inputStream = getClass().getResourceAsStream("/" + globalScope.getValue().asText().trim())){
            ContextIgnore contextIgnoreExtended = ObjectMapperFactory.createJson().convertValue(new Yaml().load(inputStream), ContextIgnore.class);
            this.pathsIgnoreExtended = contextIgnoreExtended.getPaths();
        }
    }
}
