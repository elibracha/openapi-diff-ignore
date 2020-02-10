package org.openapi.diff.ignore.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.openapi.diff.ignore.models.ignore.GlobalIgnore;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ContextDeserializer extends StdDeserializer<GlobalIgnore> {

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

        for (Iterator<Map.Entry<String, JsonNode>> globalIt = node.fields(); globalIt.hasNext(); ) {
            Map.Entry<String, JsonNode> Globallt = globalIt.next();
            if (Globallt.getKey().equals("project")) {
                System.out.println(Globallt.getValue());
            }
            if (Globallt.getKey().equals("version")) {
                System.out.println(Globallt.getValue());
            }
            if (Globallt.getKey().equals("info")) {
                System.out.println(Globallt.getValue());
            }
            if (Globallt.getKey().equals("paths") && Globallt.getValue().isContainerNode()) {
                for (Iterator<Map.Entry<String, JsonNode>> pathsIt = Globallt.getValue().fields(); pathsIt.hasNext(); ) {
                    Map.Entry<String, JsonNode> pathElt = pathsIt.next();
                    System.out.println(pathElt);
                }
            }
        }

        System.out.println("sadsadsadsad");

        return null;
    }
}