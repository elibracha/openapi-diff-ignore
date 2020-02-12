package org.openapi.diff.ignore.models;

import org.openapi.diff.ignore.models.ignore.ContextIgnore;

public class IgnoreOpenApi {

    private boolean validIgnore;
    private ContextIgnore ignore;

    public IgnoreOpenApi(ContextIgnore ignore) {
        this.ignore = ignore;
    }

    public ContextIgnore getIgnore() {
        return ignore;
    }

    public boolean isValidIgnore() {
        return validIgnore;
    }

    public IgnoreOpenApi setValidIgnore(boolean validIgnore) {
        this.validIgnore = validIgnore;
        return this;
    }
}
