package org.openapi.diff.ignore.models.validations.enums;

public enum OperationSupport {

    REQUEST("request"),
    RESPONSE("response"),
    SECURITY("security"),
    PARAMETERS("parameters");

    private String value;

    OperationSupport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
