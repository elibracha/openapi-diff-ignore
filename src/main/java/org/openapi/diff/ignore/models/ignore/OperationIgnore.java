package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.OperationDeserializer;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = OperationDeserializer.class)
public class OperationIgnore {

    private RequestIgnore request;
    private ResponseIgnore response;
    private ParametersIgnore parameters;
    private SecurityIgnore security;

}
