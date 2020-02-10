package org.openapi.diff.ignore.models;

import org.openapi.diff.ignore.models.ignore.GlobalIgnore;

public class IgnoreOpenApi {

    private boolean validIgnore;
    private GlobalIgnore ignore;

    public IgnoreOpenApi(GlobalIgnore ignore) {
        this.ignore = ignore;
    }

    public GlobalIgnore getIgnore() {
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
