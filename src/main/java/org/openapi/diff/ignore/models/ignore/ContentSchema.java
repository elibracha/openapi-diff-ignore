package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.deserializers.ContentSchemaDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ContentSchemaDeserializer.class)
public class ContentSchema extends IgnoreElemnt {
    private ContentProperties schema;
}
