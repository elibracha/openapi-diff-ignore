package org.openapi.diff.ignore.deserializes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.openapi.diff.ignore.ObjectMapperFactory;
import org.openapi.diff.ignore.exceptions.SpecificationSupportException;
import org.openapi.diff.ignore.models.SpecConstants;
import org.openapi.diff.ignore.models.ignore.ContentProperties;
import org.openapi.diff.ignore.models.ignore.ContentSchema;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ContentSchemaDeserializer extends AbstractDeserializer<ContentSchema> {

    public ContentSchemaDeserializer() {
        super(ContentSchema.class);
    }

    @Override
    public ContentSchema deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode contentSchemaScope = jsonParser.getCodec().readTree(jsonParser);
        ContentSchema contentSchema = (ContentSchema) preProcess(new ContentSchema(), contentSchemaScope);


        for (Iterator<Map.Entry<String, JsonNode>> it = contentSchemaScope.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> contentPropertiesScope = it.next();

            switch (contentPropertiesScope.getKey()) {
                case SpecConstants.ContentSchemaEntries.SCHEMA:
                    ContentProperties contentIgnore = ObjectMapperFactory.createYaml().convertValue(contentPropertiesScope.getValue(), ContentProperties.class);
                    contentSchema.setSchema(contentIgnore);
                    break;
                default:
                    throw new SpecificationSupportException(String.format(
                            "Specification does not support value \"%s\" as an schema ignore type, please referenced the documentation for supported entries.",
                            contentPropertiesScope.getKey()));
            }
        }

        return contentSchema;
    }
}
