package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openapi.diff.ignore.context.ParametersDeserializer;

import java.util.List;

@AllArgsConstructor
@Data
@JsonDeserialize(using = ParametersDeserializer.class)
public class ParametersIgnore {
    private List<String> parameters;
}
