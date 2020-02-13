package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.deserializers.ResponseDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ResponseDeserializer.class)
public class ResponseIgnore extends IgnoreElemnt {
    private Map<String, StatusIgnore> response;
}
