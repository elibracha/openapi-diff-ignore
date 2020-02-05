package org.openapi.diff.ignore.models.validations.enums;

public enum OperationSupport {

    POST("post"),
    GET("get"),
    PUT("put"),
    DELETE("delete"),
    PATCH("patch"),
    HEAD("head"),
    CONNECT("connect"),
    OPTIONS("options"),
    TRACE("trace");

    private String value;

    OperationSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
