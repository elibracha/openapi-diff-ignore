package org.openapi.diff.ignore.models.validations.enums;

public enum ResponseSupport {

    INFO("info"),
    STATUS("status");

    private String value;

    ResponseSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
