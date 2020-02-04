package com.poalim.parsers.models;

import java.util.Objects;

public class OperationIgnore {

    private PathIgnore post;
    private PathIgnore get;
    private PathIgnore put;
    private PathIgnore delete;
    private PathIgnore patch;
    private PathIgnore head;
    private PathIgnore connect;
    private PathIgnore options;
    private PathIgnore trace;

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
        return Objects.equals(post, that.post) &&
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
        return Objects.hash(post, get, put, delete, patch, head, connect, options, trace);
    }

    @Override
    public String toString() {
        return "OperationIgnore{" +
                "post=" + post +
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
