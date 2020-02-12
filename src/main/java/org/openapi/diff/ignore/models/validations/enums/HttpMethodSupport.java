package org.openapi.diff.ignore.models.validations.enums;

public enum HttpMethodSupport {

    POST("post"),
    GET("get"),
    PUT("put"),
    DELETE("delete"),
    PATCH("patch"),
    HEAD("head"),
    OPTIONS("options"),
    TRACE("trace");

    private String value;

    HttpMethodSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
