package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.ContentDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ContentDeserializer.class)
public class Content extends IgnoreElemnt {
    private boolean newContent;
    private Map<String, ContentSchema> contentSchemas;
}
