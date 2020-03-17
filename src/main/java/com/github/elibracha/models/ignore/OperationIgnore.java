package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.OperationDeserializer;
import com.github.elibracha.models.IgnoreElemnt;
import com.github.elibracha.models.SpecConstants;

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
