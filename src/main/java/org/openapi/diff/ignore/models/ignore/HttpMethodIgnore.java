package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.HttpMethodDeserializer;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = HttpMethodDeserializer.class)
public class HttpMethodIgnore {

    private PathIgnore post;
    private PathIgnore get;
    private PathIgnore put;
    private PathIgnore delete;
    private PathIgnore patch;
    private PathIgnore head;
    private PathIgnore connect;
    private PathIgnore options;
    private PathIgnore trace;

    public PathIgnore checkIfIgnoreExist(String method) {
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
