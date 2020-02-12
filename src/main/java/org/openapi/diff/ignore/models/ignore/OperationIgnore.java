package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.OperationDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;
import org.openapi.diff.ignore.models.SpecConstants;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = OperationDeserializer.class)
public class OperationIgnore extends IgnoreElemnt {

    private RequestIgnore request;
    private ResponseIgnore response;
    private ParametersIgnore parameters;
    private SecurityIgnore security;

    public IgnoreElemnt checkIfIgnoreExist(String type) {
        switch (type) {
            case SpecConstants.OperationIgnoresEntries.PARAMETERS:
                return this.parameters;
            case SpecConstants.OperationIgnoresEntries.REQUEST:
                return this.request;
            case SpecConstants.OperationIgnoresEntries.RESPONSE:
                return this.response;
            case SpecConstants.OperationIgnoresEntries.SECURITY:
                return this.security;
            default:
                return null;
        }
    }

}
