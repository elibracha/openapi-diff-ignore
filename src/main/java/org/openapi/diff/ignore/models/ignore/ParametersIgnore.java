package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.ParametersDeserializer;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ParametersDeserializer.class)
public class ParametersIgnore {
}
