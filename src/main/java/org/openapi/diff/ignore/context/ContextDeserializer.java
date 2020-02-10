package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.GlobalIgnore;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContextDeserializer extends StdDeserializer<GlobalIgnore> {

    private static final List<String> versions = Arrays.asList(
            "1.0.0"
    );

    protected ContextDeserializer(Class<?> vc) {
        super(vc);
    }

    protected ContextDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ContextDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public GlobalIgnore deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        GlobalIgnore globalIgnore = new GlobalIgnore();

        for (Iterator<Map.Entry<String, JsonNode>> globalIt = node.fields(); globalIt.hasNext(); ) {
            Map.Entry<String, JsonNode> global = globalIt.next();
            if (global.getKey().equals("version")) {
                if (versions.contains(global.getValue().asText())) {
                    globalIgnore.setVersion(global.getValue().asText());
                } else {
            
                }
            }

            if (global.getKey().equals("project")) {
                System.out.println(global.getValue());
            }

            if (global.getKey().equals("info")) {
                System.out.println(global.getValue());
            }

            if (global.getKey().equals("paths") && global.getValue().isContainerNode()) {
                for (Iterator<Map.Entry<String, JsonNode>> pathsIt = global.getValue().fields(); pathsIt.hasNext(); ) {

                    Map.Entry<String, JsonNode> pathElt = pathsIt.next();
                    JsonNode operations = pathElt.getValue();

                    for (Iterator<Map.Entry<String, JsonNode>> operationIt = operations.fields(); operationIt.hasNext(); ) {
                        Map.Entry<String, JsonNode> operation = operationIt.next();
                        JsonNode operationIgnores = operation.getValue();


                        for (Iterator<Map.Entry<String, JsonNode>> ignoresIt = operationIgnores.fields(); ignoresIt.hasNext(); ) {
                            Map.Entry<String, JsonNode> ignores = ignoresIt.next();

                        }
                    }
                }
            }
        }

        System.out.println("sadsadsadsad");

        return null;
    }
}