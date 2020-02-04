package com.poalim.parsers.models;

import java.util.Objects;

public class PathIgnore {

    private ParamsIgnore paramsIgnore;
    private RequestIgnore requestIgnore;
    private ResponseIgnore responseIgnore;
    private String method;

    public PathIgnore() {
    }

    public PathIgnore(ParamsIgnore paramsIgnore, RequestIgnore requestIgnore, ResponseIgnore responseIgnore, String method) {
        this.paramsIgnore = paramsIgnore;
        this.requestIgnore = requestIgnore;
        this.responseIgnore = responseIgnore;
        this.method = method;
    }

    public ParamsIgnore getParamsIgnore() {
        return paramsIgnore;
    }

    public void setParamsIgnore(ParamsIgnore paramsIgnore) {
        this.paramsIgnore = paramsIgnore;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathIgnore that = (PathIgnore) o;
        return Objects.equals(paramsIgnore, that.paramsIgnore) &&
                Objects.equals(requestIgnore, that.requestIgnore) &&
                Objects.equals(responseIgnore, that.responseIgnore) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paramsIgnore, requestIgnore, responseIgnore, method);
    }

    @Override
    public String toString() {
        return "PathIgnore{" +
                "paramsIgnore=" + paramsIgnore +
                ", requestIgnore=" + requestIgnore +
                ", responseIgnore=" + responseIgnore +
                ", method='" + method + '\'' +
                '}';
    }
}
