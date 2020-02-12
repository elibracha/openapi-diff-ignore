package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openapi.diff.ignore.context.ParametersDeserializer;

@AllArgsConstructor
@Data
@JsonDeserialize(using = ParametersDeserializer.class)
public class ParametersIgnore {
}
