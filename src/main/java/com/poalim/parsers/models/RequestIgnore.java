package com.poalim.parsers.models;

import java.util.Objects;

public class RequestIgnore {

    private String contentType;
    private ParamsIgnore params;

    public RequestIgnore() { }

    public RequestIgnore(String contentType, ParamsIgnore params) {
        this.contentType = contentType;
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestIgnore that = (RequestIgnore) o;
        return Objects.equals(contentType, that.contentType) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, params);
    }

    @Override
    public String toString() {
        return "RequestIgnore{" +
                "contentType='" + contentType + '\'' +
                ", params=" + params +
                '}';
    }
}
