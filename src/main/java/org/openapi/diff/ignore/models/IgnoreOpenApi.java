package org.openapi.diff.ignore.models;

import org.openapi.diff.ignore.models.ignore.GlobalIgnore;

public class IgnoreOpenApi {

    private GlobalIgnore ignore;

    public IgnoreOpenApi(GlobalIgnore ignore) {
        this.ignore = ignore;
    }

    public GlobalIgnore getIgnore() {
        return ignore;
    }
}
