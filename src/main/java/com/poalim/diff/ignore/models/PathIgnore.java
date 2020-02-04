package com.poalim.diff.ignore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PathIgnore {

    @JsonProperty("request")
    private RequestIgnore requestIgnore;
    @JsonProperty("response")
    private ResponseIgnore responseIgnore;
    private String method;
    private String type;
    private String info;

    public PathIgnore() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
                Objects.equals(type, that.type) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestIgnore, responseIgnore, method, type, info);
    }

    @Override
    public String toString() {
        return "PathIgnore{" +
                "requestIgnore=" + requestIgnore +
                ", responseIgnore=" + responseIgnore +
                ", method='" + method + '\'' +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
