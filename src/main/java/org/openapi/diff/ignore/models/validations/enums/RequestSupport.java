package org.openapi.diff.ignore.models.validations.enums;

public enum RequestSupport {

    CONTENT_TYPE("content-type"),
    PARAMETERS("parameters");

    private String value;

    RequestSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

