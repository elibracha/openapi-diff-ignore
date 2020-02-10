package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class OperationIgnore {

    @JsonProperty("ignore-type")
    private String ignoreType;
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

    public String getIgnoreType() {
        return ignoreType;
    }

    public void setIgnoreType(String ignoreType) {
        this.ignoreType = ignoreType;
    }

    public PathIgnore getPost() {
        return post;
    }

    public void setPost(PathIgnore post) {
        this.post = post;
    }

    public PathIgnore getGet() {
        return get;
    }

    public void setGet(PathIgnore get) {
        this.get = get;
    }

    public PathIgnore getPut() {
        return put;
    }

    public void setPut(PathIgnore put) {
        this.put = put;
    }

    public PathIgnore getDelete() {
        return delete;
    }

    public void setDelete(PathIgnore delete) {
        this.delete = delete;
    }

    public PathIgnore getPatch() {
        return patch;
    }

    public void setPatch(PathIgnore patch) {
        this.patch = patch;
    }

    public PathIgnore getHead() {
        return head;
    }

    public void setHead(PathIgnore head) {
        this.head = head;
    }

    public PathIgnore getConnect() {
        return connect;
    }

    public void setConnect(PathIgnore connect) {
        this.connect = connect;
    }

    public PathIgnore getOptions() {
        return options;
    }

    public void setOptions(PathIgnore options) {
        this.options = options;
    }

    public PathIgnore getTrace() {
        return trace;
    }

    public void setTrace(PathIgnore trace) {
        this.trace = trace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationIgnore that = (OperationIgnore) o;
        return Objects.equals(ignoreType, that.ignoreType) &&
                Objects.equals(post, that.post) &&
                Objects.equals(get, that.get) &&
                Objects.equals(put, that.put) &&
                Objects.equals(delete, that.delete) &&
                Objects.equals(patch, that.patch) &&
                Objects.equals(head, that.head) &&
                Objects.equals(connect, that.connect) &&
                Objects.equals(options, that.options) &&
                Objects.equals(trace, that.trace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ignoreType, post, get, put, delete, patch, head, connect, options, trace);
    }

    @Override
    public String toString() {
        return "OperationIgnore{" +
                "ignoreType='" + ignoreType + '\'' +
                ", post=" + post +
                ", get=" + get +
                ", put=" + put +
                ", delete=" + delete +
                ", patch=" + patch +
                ", head=" + head +
                ", connect=" + connect +
                ", options=" + options +
                ", trace=" + trace +
                '}';
    }
}
