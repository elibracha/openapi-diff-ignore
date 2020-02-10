package org.openapi.diff.ignore.exceptions;

import java.io.IOException;

public class InvalidVersionException extends IOException {

    public InvalidVersionException(String s) {
        super(s);
    }
}
