package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HttpMethodIgnore {

    private PathsIgnore post;
    private PathsIgnore get;
    private PathsIgnore put;
    private PathsIgnore delete;
    private PathsIgnore patch;
    private PathsIgnore head;
    private PathsIgnore connect;
    private PathsIgnore options;
    private PathsIgnore trace;

    public PathsIgnore checkIfIgnoreExist(String method) {
        switch (method) {
            case "post":
                return this.post;
            case "get":
                return this.get;
            case "put":
                return this.put;
            case "delete":
                return this.delete;
            case "connect":
                return this.connect;
            case "head":
                return this.head;
            case "options":
                return this.options;
            case "trace":
                return this.trace;
            case "patch":
                return this.patch;
            default:
                return null;
        }
    }
}
