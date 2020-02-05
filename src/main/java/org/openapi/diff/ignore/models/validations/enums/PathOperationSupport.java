package org.openapi.diff.ignore.models.validations.enums;

public enum PathOperationSupport {

    IGNORE("ignore"),
    IGNORE_TYPE("ignore-type");

    private String value;

    PathOperationSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
