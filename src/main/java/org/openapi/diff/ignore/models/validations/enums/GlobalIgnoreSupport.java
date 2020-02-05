package org.openapi.diff.ignore.models.validations.enums;

public enum GlobalIgnoreSupport {

    VERSION("version"),
    INFO("info"),
    PROJECT("project"),
    PATHS("paths");

    private String value;

    GlobalIgnoreSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
