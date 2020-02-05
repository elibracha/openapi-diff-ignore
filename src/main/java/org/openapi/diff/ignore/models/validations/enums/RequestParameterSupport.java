package org.openapi.diff.ignore.models.validations.enums;

public enum RequestParameterSupport {

    CONTENT("content");

    private String value;

    RequestParameterSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
