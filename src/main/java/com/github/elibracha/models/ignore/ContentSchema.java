package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.ContentSchemaDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ContentSchemaDeserializer.class)
public class ContentSchema extends IgnoreElemnt {
    private ContentProperties schema;
}
