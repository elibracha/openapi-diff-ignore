package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.elibracha.deserializers.HttpMethodDeserializer;
import com.github.elibracha.models.IgnoreElemnt;
import com.github.elibracha.models.SpecConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = HttpMethodDeserializer.class)
public class HttpMethodIgnore extends IgnoreElemnt {

    private OperationIgnore post;
    private OperationIgnore get;
    private OperationIgnore put;
    private OperationIgnore delete;
    private OperationIgnore patch;
    private OperationIgnore head;
    private OperationIgnore options;
    private OperationIgnore trace;

    public OperationIgnore checkIfIgnoreExist(String method) {
        switch (method) {
            case SpecConstants.HttpMethods.POST:
                return this.post;
            case SpecConstants.HttpMethods.GET:
                return this.get;
            case SpecConstants.HttpMethods.PUT:
                return this.put;
            case SpecConstants.HttpMethods.DELETE:
                return this.delete;
            case SpecConstants.HttpMethods.HEAD:
                return this.head;
            case SpecConstants.HttpMethods.OPTIONS:
                return this.options;
            case SpecConstants.HttpMethods.TRACE:
                return this.trace;
            case SpecConstants.HttpMethods.PATCH:
                return this.patch;
            default:
                return null;
        }
    }
}
