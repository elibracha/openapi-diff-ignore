package org.openapi.diff.ignore.models;

import org.openapi.diff.ignore.models.ignore.ContextIgnore;

public class OpenApiIgnore {

    private boolean validIgnore;
    private ContextIgnore ignore;

    public OpenApiIgnore(ContextIgnore ignore) {
        this.ignore = ignore;
    }

    public ContextIgnore getIgnore() {
        return ignore;
    }

    public boolean isValidIgnore() {
        return validIgnore;
    }

    public OpenApiIgnore setValidIgnore(boolean validIgnore) {
        this.validIgnore = validIgnore;
        return this;
    }
}
