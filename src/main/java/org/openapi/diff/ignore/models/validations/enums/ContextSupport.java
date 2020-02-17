package org.openapi.diff.ignore.models.validations.enums;

public enum ContextSupport {

    EXTENDS("extends"),
    VERSION("version"),
    INFO("info"),
    PROJECT("project"),
    PATHS("paths");

    private String value;

    ContextSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
