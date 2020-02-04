package com.poalim.parsers.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PathIgnore {

    @JsonProperty("request")
    private RequestIgnore requestIgnore;
    @JsonProperty("response")
    private ResponseIgnore responseIgnore;

    private String method;
    private String type;

    public PathIgnore() {
    }

    public PathIgnore(RequestIgnore requestIgnore, ResponseIgnore responseIgnore, String method, String type) {
        this.requestIgnore = requestIgnore;
        this.responseIgnore = responseIgnore;
        this.method = method;
        this.type = type;
    }

    public RequestIgnore getRequestIgnore() {
        return requestIgnore;
    }

    public void setRequestIgnore(RequestIgnore requestIgnore) {
        this.requestIgnore = requestIgnore;
    }

    public ResponseIgnore getResponseIgnore() {
        return responseIgnore;
    }

    public void setResponseIgnore(ResponseIgnore responseIgnore) {
        this.responseIgnore = responseIgnore;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathIgnore that = (PathIgnore) o;
        return Objects.equals(requestIgnore, that.requestIgnore) &&
                Objects.equals(responseIgnore, that.responseIgnore) &&
                Objects.equals(method, that.method) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestIgnore, responseIgnore, method, type);
    }

    @Override
    public String toString() {
        return "PathIgnore{" +
                "requestIgnore=" + requestIgnore +
                ", responseIgnore=" + responseIgnore +
                ", method='" + method + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
